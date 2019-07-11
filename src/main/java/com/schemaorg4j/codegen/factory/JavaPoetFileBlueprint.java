package com.schemaorg4j.codegen.factory;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.util.ArrayList;
import java.util.List;

public class JavaPoetFileBlueprint {

    private List<TypeSpec> types;
    private List<MethodSpec> methods;
    private List<FieldSpec> fields;

    public JavaPoetFileBlueprint() {
        this.methods = new ArrayList<>();
        this.fields = new ArrayList<>();
    }

    public void addMethod(MethodSpec methodSpec) {
        this.methods.add(methodSpec);
    }

    public void addField(FieldSpec fieldSpec) {
        this.fields.add(fieldSpec);
    }

    public void addType(TypeSpec typeSpec) {
        this.types.add(typeSpec);
    }

    public List<TypeSpec> getTypes() {
        return types;
    }

    public List<FieldSpec> getFields() {
        return fields;
    }

    public List<MethodSpec> getMethods() {
        return methods;
    }
}
