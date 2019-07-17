package com.schemaorg4j.codegen.factory.types;

import static com.schemaorg4j.codegen.StringUtils.capitalize;
import static com.schemaorg4j.codegen.factory.types.TypeUtil.getUnderlyingType;
import static com.schemaorg4j.codegen.factory.types.TypeUtil.isDataType;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;

public class MethodUtil {

    public static List<MethodSpec> getGetter(FieldSpec fieldSpec) {
        return new ArrayList<MethodSpec>() {{
            add(getActualGetter(fieldSpec));
            if (isDataType(fieldSpec.type)) {
                add(getDataGetter(fieldSpec));
            }
        }};
    }

    public static List<MethodSpec> getSetter(FieldSpec fieldSpec) {
        return new ArrayList<MethodSpec>() {{
            add(getActualSetter(fieldSpec));
            if (isDataType(fieldSpec.type)) {
                add(getDataSetter(fieldSpec));
            }
        }};
    }

    private static MethodSpec getActualGetter(FieldSpec fieldSpec) {
        return MethodSpec
            .methodBuilder(
                "get" + capitalize(fieldSpec.name) + (isDataType(fieldSpec.type) ? "Data" : ""))
            .addModifiers(Modifier.PUBLIC)
            .addStatement("return $N", fieldSpec.name)
            .returns(fieldSpec.type)
            .build();
    }

    private static MethodSpec getActualSetter(FieldSpec fieldSpec) {
        return MethodSpec
            .methodBuilder(
                "set" + capitalize(fieldSpec.name) + (isDataType(fieldSpec.type) ? "Data" : ""))
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ParameterSpec.builder(fieldSpec.type, fieldSpec.name)
                .addAnnotations(fieldSpec.annotations).build())
            .addStatement("this.$N = $N", fieldSpec.name, fieldSpec.name)
            .build();
    }

    private static MethodSpec getDataGetter(FieldSpec fieldSpec) {
        return MethodSpec
            .methodBuilder("get" + capitalize(fieldSpec.name))
            .addModifiers(Modifier.PUBLIC)
            .addStatement("return $N == null ? null : $N.getValue()", fieldSpec.name,
                fieldSpec.name)
            .returns(getUnderlyingType(fieldSpec.type))
            .build();
    }

    private static MethodSpec getDataSetter(FieldSpec fieldSpec) {
        TypeName underlyingType = getUnderlyingType(fieldSpec.type);
        return MethodSpec
            .methodBuilder("set" + capitalize(fieldSpec.name))
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ParameterSpec.builder(underlyingType, fieldSpec.name)
                .addAnnotations(fieldSpec.annotations).build())
            .addCode("if (this.$N == null) {"
                    + "    this.$N = new $T($N);"
                    + "} else {"
                    + "    this.$N.setValue($N);"
                    + "}",
                fieldSpec.name, fieldSpec.name, fieldSpec.type, fieldSpec.name, fieldSpec.name,
                fieldSpec.name)
            .build();
    }


}
