package com.schemaorg4j.codegen.factory.types;

import static com.schemaorg4j.codegen.StringUtils.capitalize;
import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.UTIL_PACKAGE;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import javax.lang.model.element.Modifier;

public class FieldUtil {

    public static FieldSpec getLensField(SchemaClass schemaClass, FieldSpec fieldSpec) {
        return getLensFieldFromLensType(getType(schemaClass, fieldSpec), fieldSpec);
    }

    public static FieldSpec getLensField(String simpleClassName, FieldSpec fieldSpec, String sourcePackage) {
        return getLensFieldFromLensType(getType(simpleClassName, fieldSpec, sourcePackage), fieldSpec);
    }

    private static FieldSpec getLensFieldFromLensType(TypeName lensType, FieldSpec fieldSpec) {
        return FieldSpec.builder(lensType, capitalize(fieldSpec.name), Modifier.PUBLIC,
                Modifier.FINAL, Modifier.STATIC)
            .initializer(getInitializer(fieldSpec))
            .build();
    }

    private static CodeBlock getInitializer(FieldSpec fieldSpec) {
        return CodeBlock.builder()
            .add(
                "new Lens<>(c -> c.get$N(), (c, fieldValue) -> { c.set$N(fieldValue); return c; })",
                capitalize(fieldSpec.name), capitalize(fieldSpec.name))
            .build();
    }

    private static TypeName getType(SchemaClass schemaClass, FieldSpec fieldSpec) {
        return getType(schemaClass.getLabel(), fieldSpec, DOMAIN_PACKAGE);
    }

    private static TypeName getType(String simpleClassName, FieldSpec fieldSpec, String sourcePackage) {
        return ParameterizedTypeName.get(ClassName.get(UTIL_PACKAGE, "Lens"),
            ClassName.get(sourcePackage, simpleClassName),
            fieldSpec.type);
    }
}
