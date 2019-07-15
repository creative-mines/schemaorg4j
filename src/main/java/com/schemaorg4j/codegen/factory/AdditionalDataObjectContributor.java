package com.schemaorg4j.codegen.factory;

import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.THING_ID;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import java.util.Objects;
import javax.lang.model.element.Modifier;

public class AdditionalDataObjectContributor implements BlueprintContributor {

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        FieldSpec fieldSpec = FieldSpec.builder(
            ClassName.get(DOMAIN_PACKAGE + ".datatypes", "SchemaOrg4JAdditionalData"), "schemaOrg4JAdditionalData",
            Modifier.PRIVATE)
            .build();
        if (Objects.equals(schemaClass.getId(), THING_ID)) {
            blueprint.addField(fieldSpec);
        } else {
            blueprint.addInheritedField(fieldSpec);
        }
    }
}
