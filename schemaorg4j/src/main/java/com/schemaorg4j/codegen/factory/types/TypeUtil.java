package com.schemaorg4j.codegen.factory.types;

import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.DATATYPES_PACKAGE;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import java.util.Objects;

public class TypeUtil {

    public static boolean isDataType(TypeName type) {
        return type.toString().startsWith(DATATYPES_PACKAGE) && !type.toString()
            .contains("SchemaOrg4J");
    }

    public static TypeName getUnderlyingType(String typeName) {
        if (Objects.equals(typeName, DATATYPES_PACKAGE + ".Date")) {
            return ClassName.get("java.time", "LocalDate");
        } else if (Objects.equals(typeName, DATATYPES_PACKAGE + ".DateTime")) {
            return ClassName.get("java.time", "ZonedDateTime");
        } else if (Objects.equals(typeName, DATATYPES_PACKAGE + ".Time")) {
            return ClassName.get("java.time", "LocalTime");
        } else if (Objects.equals(typeName, DATATYPES_PACKAGE + ".Integer")) {
            return ClassName.get("java.lang", "Integer");
        } else if (Objects.equals(typeName, DATATYPES_PACKAGE + ".Float")) {
            return ClassName.get("java.lang", "Float");
        } else if (Objects.equals(typeName, DATATYPES_PACKAGE + ".Number")) {
            return ClassName.get("java.lang", "Float");
        } else if (Objects.equals(typeName, DATATYPES_PACKAGE + ".Text")) {
            return ClassName.get("java.lang", "String");
        } else if (Objects.equals(typeName, DATATYPES_PACKAGE + ".URL")) {
            return ClassName.get("java.lang", "String");
        } else {
            return ClassName.get("java.lang", "Boolean");
        }
    }


    public static TypeName getUnderlyingType(TypeName type) {
        return getUnderlyingType(type.toString());
    }
}
