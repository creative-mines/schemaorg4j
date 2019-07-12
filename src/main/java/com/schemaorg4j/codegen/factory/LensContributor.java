package com.schemaorg4j.codegen.factory;

import static com.schemaorg4j.codegen.StringUtils.capitalize;
import static com.schemaorg4j.codegen.StringUtils.orLabelFromId;
import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.UTIL_PACKAGE;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import javax.lang.model.element.Modifier;

public class LensContributor implements BlueprintContributor {

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        blueprint.getFields().forEach(fieldSpec -> {
            blueprint.addLensField(
                FieldSpec.builder(getType(schemaClass, fieldSpec), capitalize(fieldSpec.name),
                    Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                    .initializer(getInitializer(schemaClass, fieldSpec))
                    .build());
        });
    }

    private CodeBlock getInitializer(SchemaClass schemaClass, FieldSpec fieldSpec) {
        return CodeBlock.builder()
            .add("new Lens<>($N::get$N, (c, fieldValue) -> { c.set$N(fieldValue); return c; })",
                schemaClass.getLabel(), capitalize(fieldSpec.name), capitalize(fieldSpec.name))
            .build();
    }

    private TypeName getType(SchemaClass schemaClass, FieldSpec fieldSpec) {
        return ParameterizedTypeName.get(ClassName.get(UTIL_PACKAGE, "Lens"),
            ClassName.get(DOMAIN_PACKAGE, schemaClass.getLabel()),
            fieldSpec.type);
    }
}
