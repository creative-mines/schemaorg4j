package com.schemaorg4j.codegen.factory.types;

import static com.schemaorg4j.codegen.StringUtils.capitalize;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import javax.lang.model.element.Modifier;

public class MethodUtil {

    public static MethodSpec getGetter(FieldSpec fieldSpec) {
        return MethodSpec
            .methodBuilder("get" + capitalize(fieldSpec.name))
            .addModifiers(Modifier.PUBLIC)
            .addStatement("return $N", fieldSpec.name)
            .returns(fieldSpec.type)
            .build();
    }

    public static MethodSpec getSetter(FieldSpec fieldSpec) {
        return MethodSpec
            .methodBuilder("set" + capitalize(fieldSpec.name))
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ParameterSpec.builder(fieldSpec.type, fieldSpec.name).build())
            .addStatement("this.$N = $N", fieldSpec.name, fieldSpec.name)
            .build();
    }
}
