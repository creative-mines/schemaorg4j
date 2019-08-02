package org.creativemines.schemaorg4j.codegen.feature;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import static org.creativemines.schemaorg4j.codegen.factory.types.MethodUtil.getGetter;
import static org.creativemines.schemaorg4j.codegen.factory.types.MethodUtil.getSetter;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;

import org.creativemines.schemaorg4j.annotations.SchemaOrg4JNoGetterSetter;

public class NextFieldFeature {

    private String packageName;

    public NextFieldFeature(String packageName) {
        this.packageName = packageName;
    }

    public FeatureResponse build(String className) {
        ArrayList<FieldSpec> fields = new ArrayList<>();
        ArrayList<MethodSpec> methods = new ArrayList<>();

        FieldSpec nextField = getNextField(className);
        fields.add(nextField);
        methods.addAll(getGetterForNextField(nextField));
        methods.addAll(getSetterForNextField(nextField));

        FieldSpec nextListField = getListField(className);
        fields.add(nextListField);
        methods.add(getAsListMethod(nextListField, className));

        return new FeatureResponse(fields, methods);
    }

    private MethodSpec getAsListMethod(FieldSpec nextListField, String className) {
        return MethodSpec.methodBuilder(String.format("as%sList", className))
            .returns(nextListField.type)
            .addModifiers(Modifier.PUBLIC)
            .addCode(CodeBlock.builder().add(""
                    + "if (this.$N != null) {\n"
                    + "    return this.$N;\n"
                    + "}\n\n"
                    + "this.$N = new $T<$L>();\n"
                    + "this.$N.add(this);"
                    + "$L tracking = this.getNext$L();\n"
                    + "while (tracking != null) {\n"
                    + "    this.$N.add(tracking);\n"
                    + "    tracking = tracking.getNext$L();\n"
                    + "}\n"
                    + "return this.$N;", nextListField.name, nextListField.name, nextListField.name,
                TypeName.get(ArrayList.class), className, nextListField.name, className, className, nextListField.name,
                className, nextListField.name)
                .build())
            .build();
    }

    private FieldSpec getListField(String className) {
        return FieldSpec.builder(
                ParameterizedTypeName.get(
                    ClassName.get("java.util", "List"),
                    ClassName.get(packageName, className)
                ), String.format("next%sList", className), Modifier.PRIVATE)
            .addAnnotation(SchemaOrg4JNoGetterSetter.class)
            .build();
    }

    private List<MethodSpec> getGetterForNextField(FieldSpec nextField) {
        return getGetter(nextField);
    }

    private List<MethodSpec> getSetterForNextField(FieldSpec nextField) {
        return getSetter(nextField);
    }

    private FieldSpec getNextField(String className) {
        return FieldSpec
            .builder(ClassName.get(packageName, className), "next" + className, Modifier.PRIVATE)
            .build();
    }
}
