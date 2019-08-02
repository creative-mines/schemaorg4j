package org.creativemines.schemaorg4j.codegen.factory;

import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;

public interface BlueprintContributor {

    void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint);
}
