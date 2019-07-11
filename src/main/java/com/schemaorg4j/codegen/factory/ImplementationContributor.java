package com.schemaorg4j.codegen.factory;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaGraph;

public class ImplementationContributor implements BlueprintContributor {

    private SchemaGraph graph;

    public ImplementationContributor(SchemaGraph graph) {
        this.graph = graph;
    }

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {

    }
}
