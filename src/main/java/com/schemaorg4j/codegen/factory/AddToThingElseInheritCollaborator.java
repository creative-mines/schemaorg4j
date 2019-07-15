package com.schemaorg4j.codegen.factory;

import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.THING_ID;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.squareup.javapoet.FieldSpec;
import java.util.Objects;

public class AddToThingElseInheritCollaborator {
    public void doIt(FieldSpec fieldSpec, SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        if (Objects.equals(schemaClass.getId(), THING_ID)) {
            blueprint.addField(fieldSpec);
        } else {
            blueprint.addInheritedField(fieldSpec);
        }
    }
}
