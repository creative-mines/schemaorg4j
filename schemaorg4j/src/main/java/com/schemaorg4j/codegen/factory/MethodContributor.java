package com.schemaorg4j.codegen.factory;

import static com.schemaorg4j.codegen.factory.types.MethodUtil.getGetter;
import static com.schemaorg4j.codegen.factory.types.MethodUtil.getSetter;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaGraph;

public class MethodContributor implements BlueprintContributor {

    private final SchemaGraph graph;

    public MethodContributor(SchemaGraph graph) {
        this.graph = graph;
    }

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        blueprint.getFields().forEach(fieldSpec -> {
            getGetter(fieldSpec).forEach(blueprint::addMethod);
            getSetter(fieldSpec).forEach(blueprint::addMethod);
        });
    }
}
