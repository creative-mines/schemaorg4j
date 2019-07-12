package com.schemaorg4j.codegen.factory;

import static com.schemaorg4j.codegen.constants.Schema4JConstants.DOMAIN_PACKAGE;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

public class InterfaceContributor implements BlueprintContributor {

    private final SchemaGraph graph;

    public InterfaceContributor(SchemaGraph graph) {
        this.graph = graph;
    }

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        Builder builder = TypeSpec
            .interfaceBuilder(ClassName.get(DOMAIN_PACKAGE, schemaClass.getLabel()));

        addMethods(builder, blueprint);
        addSuperInterfaces(builder, schemaClass);

        blueprint.addType(builder.build());
    }

    private void addSuperInterfaces(Builder builder, SchemaClass schemaClass) {
        schemaClass.getSubclassOfIds()
            .stream()
            .map(graph::getClass)
            .map(SchemaClass::getLabel)
            .sorted()
            .forEach(superTypeName -> builder
                .addSuperinterface(ClassName.get(DOMAIN_PACKAGE, superTypeName)));
    }

    private void addMethods(Builder builder, JavaPoetFileBlueprint blueprint) {
        builder.addMethods(blueprint.getMethods().stream().map(methodSpec -> MethodSpec
                .methodBuilder(methodSpec.name)
                .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
                .returns(methodSpec.returnType)
                .addParameters(methodSpec.parameters)
                .build()
        ).collect(Collectors.toList()));
    }
}
