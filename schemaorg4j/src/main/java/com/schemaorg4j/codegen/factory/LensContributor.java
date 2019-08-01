package com.schemaorg4j.codegen.factory;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.factory.types.FieldUtil;

public class LensContributor implements BlueprintContributor {

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        blueprint.getFields().forEach(
            fieldSpec -> blueprint.addLensField(FieldUtil.getLensField(schemaClass, fieldSpec)));
    }
}
