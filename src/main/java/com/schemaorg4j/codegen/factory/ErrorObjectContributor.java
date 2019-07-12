package com.schemaorg4j.codegen.factory;

import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.DOMAIN_PACKAGE;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import java.util.Objects;
import javax.lang.model.element.Modifier;

public class ErrorObjectContributor implements BlueprintContributor {

    private static final String THING_ID = "http://schema.org/Thing";

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        if (Objects.equals(schemaClass.getId(), THING_ID)) {
            blueprint.addField(FieldSpec.builder(
                ClassName.get(DOMAIN_PACKAGE + ".error", "SchemaOrg4JError"), "error",
                Modifier.PRIVATE)
                .build());
        }
    }
}
