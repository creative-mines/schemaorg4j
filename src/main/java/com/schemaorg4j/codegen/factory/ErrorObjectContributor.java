package com.schemaorg4j.codegen.factory;

import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.DOMAIN_PACKAGE;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import javax.lang.model.element.Modifier;

public class ErrorObjectContributor implements BlueprintContributor {

    private final AddToThingElseInheritCollaborator collaborator;

    public ErrorObjectContributor() {
        this.collaborator = new AddToThingElseInheritCollaborator();
    }

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        collaborator.doIt(getFieldSpec(), schemaClass, blueprint);
    }

    private FieldSpec getFieldSpec() {
        return FieldSpec.builder(ParameterizedTypeName.get(
            ClassName.get("java.util", "List"),
            ClassName.get(DOMAIN_PACKAGE + ".error", "SchemaOrg4JError")
        ), "schemaOrg4JErrors", Modifier.PRIVATE).build();
    }

}
