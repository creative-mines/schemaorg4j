package org.creativemines.schemaorg4j.codegen.factory;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.COMBO_TYPE_PACKAGE;
import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.ENUM_PACKAGE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.factory.types.CachingTypeFactory;
import org.creativemines.schemaorg4j.codegen.factory.types.InheritedFieldContributor;
import org.creativemines.schemaorg4j.codegen.factory.types.MultiTypeProducingTypeFactory;
import org.creativemines.schemaorg4j.codegen.factory.types.SimpleTypeFactory;
import org.creativemines.schemaorg4j.codegen.factory.types.TypeFactory;

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
            add(new AdditionalDataObjectContributor());
            add(new SimpleValueFieldContributor());
            add(new MethodContributor(graph));
            add(new LensContributor());
            add(new InheritedFieldContributor(graph, typeFactory));
            add(new NextFieldContributor());
            add(new InterfaceContributor(graph));
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

        return fromSchemaOrg;
    }
}
