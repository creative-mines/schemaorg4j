package org.creativemines.schemaorg4j.codegen.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DOMAIN_PACKAGE;

import javax.lang.model.element.Modifier;

import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;

public class AdditionalDataObjectContributor implements BlueprintContributor {

    private final AddToThingElseInheritCollaborator collaborator;

    public AdditionalDataObjectContributor() {
        this.collaborator = new AddToThingElseInheritCollaborator();
    }

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        collaborator.doIt(getFieldSpec(), schemaClass, blueprint);
    }

    private FieldSpec getFieldSpec() {
        return FieldSpec
            .builder(ClassName.get(DOMAIN_PACKAGE + ".datatypes", "SchemaOrg4JAdditionalData"),
                "schemaOrg4JAdditionalData", Modifier.PRIVATE).build();
    }

}
