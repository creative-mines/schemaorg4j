package org.creativemines.schemaorg4j.codegen.factory;

import com.squareup.javapoet.FieldSpec;

import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.THING_ID;

import java.util.Objects;

import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;

public class AddToThingElseInheritCollaborator {
    public void doIt(FieldSpec fieldSpec, SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        if (Objects.equals(schemaClass.getId(), THING_ID)) {
            blueprint.addField(fieldSpec);
        } else {
            blueprint.addInheritedField(fieldSpec);
        }
    }
}
