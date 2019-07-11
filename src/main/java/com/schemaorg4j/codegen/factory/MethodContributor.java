package com.schemaorg4j.codegen.factory;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

public class MethodContributor implements BlueprintContributor {

    private final SchemaGraph graph;

    public MethodContributor(SchemaGraph graph) {
        this.graph = graph;
    }

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        blueprint.getFields().forEach(fieldSpec -> {
            blueprint.addMethod(contributeSetter(fieldSpec));
            blueprint.addMethod(contributeGetter(fieldSpec));
        });
    }

    private MethodSpec contributeGetter(FieldSpec fieldSpec) {
        return MethodSpec
            .methodBuilder("get" + capitalize(fieldSpec.name))
            .addStatement("return $N", fieldSpec.name)
            .returns(fieldSpec.type)
            .build();
    }

    private String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private MethodSpec contributeSetter(FieldSpec fieldSpec) {
        return MethodSpec
            .methodBuilder("set" + capitalize(fieldSpec.name))
            .addParameter(ParameterSpec.builder(fieldSpec.type, fieldSpec.name).build())
            .addStatement("this.$N = $N", fieldSpec.name, fieldSpec.name)
            .build();
    }
}
