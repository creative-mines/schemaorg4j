package com.schemaorg4j.codegen.factory;

import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.DOMAIN_PACKAGE;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import javax.lang.model.element.Modifier;

public class ErrorObjectContributor implements BlueprintContributor {

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        blueprint.addField(FieldSpec.builder(
            ClassName.get(DOMAIN_PACKAGE + ".error", "SchemaOrg4JError"), "error", Modifier.PRIVATE)
            .build());
    }
}
