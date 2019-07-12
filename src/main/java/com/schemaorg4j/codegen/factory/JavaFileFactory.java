package com.schemaorg4j.codegen.factory;

import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.factory.types.CachingTypeFactory;
import com.schemaorg4j.codegen.factory.types.MultiTypeProducingTypeFactory;
import com.schemaorg4j.codegen.factory.types.SimpleTypeFactory;
import com.schemaorg4j.codegen.factory.types.TypeFactory;
import com.squareup.javapoet.JavaFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaFileFactory {

    private final String packageRoot;

    public JavaFileFactory(String packageRoot) {
        this.packageRoot = packageRoot;
    }

    public Collection<JavaFile> buildJavaFiles(SchemaGraph graph) {
        SimpleTypeFactory simpleFactory = new SimpleTypeFactory(graph);
        MultiTypeProducingTypeFactory multiTypeProducingTypeFactory = new MultiTypeProducingTypeFactory(
            simpleFactory, graph);
        TypeFactory typeFactory = new CachingTypeFactory(multiTypeProducingTypeFactory);

        ArrayList<BlueprintContributor> chain = new ArrayList<BlueprintContributor>() {{
            add(new FieldContributor(graph, typeFactory));
            add(new EnumMemberContributor(graph));
            add(new MethodContributor(graph));
            add(new LensContributor(graph));
            add(new InterfaceContributor(graph));
            add(new ErrorObjectContributor());
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
            .map(typeSpec -> JavaFile.builder(packageRoot, typeSpec).build())
            .collect(Collectors.toList());

        List<JavaFile> comboTypes = multiTypeProducingTypeFactory
            .getEmittedTypes()
            .stream()
            .map(typeSpec -> JavaFile.builder(packageRoot, typeSpec).build())
            .collect(Collectors.toList());

        fromSchemaOrg.addAll(comboTypes);
        return fromSchemaOrg;
    }

}
