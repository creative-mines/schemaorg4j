package com.schemaorg4j.codegen.factory;

import com.schemaorg4j.codegen.domain.SchemaClass;

public interface BlueprintContributor {

    void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint);
}
