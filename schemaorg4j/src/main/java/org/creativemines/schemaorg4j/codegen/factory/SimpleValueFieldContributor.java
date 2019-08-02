package org.creativemines.schemaorg4j.codegen.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import javax.lang.model.element.Modifier;

import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;

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
