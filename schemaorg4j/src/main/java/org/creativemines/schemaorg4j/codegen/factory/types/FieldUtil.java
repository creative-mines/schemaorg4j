package org.creativemines.schemaorg4j.codegen.factory.types;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.UTIL_PACKAGE;
import static org.creativemines.schemaorg4j.codegen.StringUtils.capitalize;
import static org.creativemines.schemaorg4j.codegen.factory.types.TypeUtil.isDataType;

import javax.lang.model.element.Modifier;

import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;

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
        String maybeData = isDataType(fieldSpec.type) ? "Data" : "";

        return CodeBlock.builder()
            .add(
                String.format("new Lens<>(c -> c.get$N%s(), (c, fieldValue) -> { c.set$N%s(fieldValue); return c; })", maybeData, maybeData),
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
