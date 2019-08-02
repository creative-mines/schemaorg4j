package org.creativemines.schemaorg4j.codegen.factory;

import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;
import org.creativemines.schemaorg4j.codegen.factory.types.FieldUtil;

public class LensContributor implements BlueprintContributor {

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        blueprint.getFields().forEach(
            fieldSpec -> blueprint.addLensField(FieldUtil.getLensField(schemaClass, fieldSpec)));
    }
}
