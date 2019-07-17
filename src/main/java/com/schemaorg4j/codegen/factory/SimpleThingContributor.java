package com.schemaorg4j.codegen.factory;

import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.THING_ID;
import static com.schemaorg4j.codegen.factory.types.MethodUtil.getGetter;
import static com.schemaorg4j.codegen.factory.types.MethodUtil.getSetter;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import java.util.Objects;
import javax.lang.model.element.Modifier;

public class SimpleThingContributor implements BlueprintContributor {

    private final AddToThingElseInheritCollaborator collaborator;

    public SimpleThingContributor() {
        this.collaborator = new AddToThingElseInheritCollaborator();
    }

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {

        FieldSpec simpleValueSpec = FieldSpec
            .builder(ClassName.get("java.lang", "String"), "simpleValue", Modifier.PRIVATE).build();

        collaborator.doIt(simpleValueSpec, schemaClass, blueprint);

        if (Objects.equals(schemaClass.getId(), THING_ID)) {
            blueprint.addMethod(
                MethodSpec
                    .methodBuilder("isSimpleThing")
                    .addModifiers(Modifier.PUBLIC, Modifier.DEFAULT)
                    .addCode(CodeBlock
                        .builder()
                        .addStatement("return false")
                        .build())
                    .returns(TypeName.BOOLEAN)
                    .build());
        }
    }
}
