package com.schemaorg4j.codegen.factory;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import javax.lang.model.element.Modifier;

public class SimpleValueFieldContributor implements BlueprintContributor {

    private final AddToThingElseInheritCollaborator collaborator;

    public SimpleValueFieldContributor() {
        this.collaborator = new AddToThingElseInheritCollaborator();
    }

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        collaborator.doIt(getSimpleValueField(), schemaClass, blueprint);
    }

    public static FieldSpec getSimpleValueField() {
        return FieldSpec
            .builder(ClassName.get("java.lang", "String"), "simpleValue", Modifier.PRIVATE).build();
    }
}
