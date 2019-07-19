package com.schemaorg4j.codegen.factory;

import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.COMBO_TYPE_PACKAGE;
import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.CUSTOM_PACKAGE;
import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.ENUM_PACKAGE;

import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.factory.types.CachingTypeFactory;
import com.schemaorg4j.codegen.factory.types.InheritedFieldContributor;
import com.schemaorg4j.codegen.factory.types.MultiTypeProducingTypeFactory;
import com.schemaorg4j.codegen.factory.types.SimpleTypeFactory;
import com.schemaorg4j.codegen.factory.types.TypeFactory;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

public class JavaFileFactory {

    private static class Container<T> {
        public T value;
    }


    public Collection<JavaFile> buildJavaFiles(SchemaGraph graph) {
        Container<TypeSpec> thingSpecContainer = new Container<>();

        SimpleTypeFactory simpleFactory = new SimpleTypeFactory(graph);
        MultiTypeProducingTypeFactory multiTypeProducingTypeFactory = new MultiTypeProducingTypeFactory(
            simpleFactory, graph);
        TypeFactory typeFactory = new CachingTypeFactory(multiTypeProducingTypeFactory);

        EnumMemberContributor enumMemberContributor = new EnumMemberContributor(graph);

        ArrayList<BlueprintContributor> chain = new ArrayList<BlueprintContributor>() {{
            add(new FieldContributor(graph, typeFactory));
            add(enumMemberContributor);
            add(new ErrorObjectContributor());
            add(new SimpleThingContributor());
            add(new AdditionalDataObjectContributor());
            add(new NextFieldContributor());
            add(new MethodContributor(graph));
            add(new LensContributor());
            add(new InterfaceContributor(graph));
            add(new InheritedFieldContributor(graph, typeFactory));
            add(new ImplementationContributor(graph));
        }};

        List<JavaFile> fromSchemaOrg = graph
            .getClasses()
            .stream()
            .map(schemaClass -> {
                JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
                chain.forEach(link -> link.contribute(schemaClass, blueprint));
                return blueprint.getTypes();
            })
            .flatMap(List::stream)
            .map(typeSpec -> {

                if (Objects.equals(typeSpec.name, "ThingImpl")) {
                    thingSpecContainer.value = typeSpec;
                }

                if (typeSpec.enumConstants != null && !typeSpec.enumConstants.isEmpty()) {
                    return JavaFile.builder(ENUM_PACKAGE, typeSpec).build();
                } else {
                    return JavaFile.builder(DOMAIN_PACKAGE, typeSpec).build();
                }
            })
            .collect(Collectors.toList());

        List<JavaFile> comboTypes = multiTypeProducingTypeFactory
            .getEmittedTypes()
            .stream()
            .map(typeSpec -> JavaFile.builder(COMBO_TYPE_PACKAGE, typeSpec).build())
            .collect(Collectors.toList());

        List<JavaFile> enumTypes = enumMemberContributor
            .getEmittedTypes()
            .stream()
            .map(typeSpec -> JavaFile.builder(ENUM_PACKAGE, typeSpec).build())
            .collect(Collectors.toList());

        fromSchemaOrg.addAll(comboTypes);
        fromSchemaOrg.addAll(enumTypes);
        fromSchemaOrg.add(simpleThing(thingSpecContainer.value));

        return fromSchemaOrg;
    }

    private JavaFile simpleThing(TypeSpec thingSpec) {

        Builder typeSpec = TypeSpec.classBuilder("SimpleThing").addSuperinterface(
            ClassName.get(DOMAIN_PACKAGE, "Thing"));

        typeSpec.addFields(thingSpec.fieldSpecs);
        typeSpec.addMethods(thingSpec.methodSpecs.stream().filter(methodSpec -> !Objects.equals(methodSpec.name, "isSimpleThing")).collect(
            Collectors.toList()));
        typeSpec.addMethod(MethodSpec.methodBuilder("isSimpleThing").addStatement("return true").returns(
            TypeName.BOOLEAN).addModifiers(Modifier.PUBLIC).build());

        return JavaFile.builder(CUSTOM_PACKAGE, typeSpec.build()).build();
    }

}
