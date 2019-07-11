package com.schemaorg4j.codegen.factory;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.factory.types.CachingTypeFactory;
import com.schemaorg4j.codegen.factory.types.SimpleTypeFactory;
import com.schemaorg4j.codegen.factory.types.TypeFactory;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JavaFileFactory {

    private final String packageRoot;

    public JavaFileFactory(String packageRoot) {
        this.packageRoot = packageRoot;
    }

    public Collection<JavaFile> buildJavaFiles(SchemaGraph graph) {
        TypeFactory typeFactory = new CachingTypeFactory(new SimpleTypeFactory(graph));

        ArrayList<BlueprintContributor> chain = new ArrayList<BlueprintContributor>() {{
            add(new FieldContributor(graph, typeFactory));
            add(new EnumMemberContributor(graph));
            add(new ErrorObjectContributor());
            add(new MethodContributor(graph));
            add(new LensContributor(graph));
            add(new ImplementationContributor(graph));
            add(new InterfaceContributor(graph));
        }};

        return graph
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
    }

}
