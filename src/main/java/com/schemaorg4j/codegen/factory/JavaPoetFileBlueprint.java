package com.schemaorg4j.codegen.factory;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.util.ArrayList;
import java.util.List;

/**
 * All methods and fields are designed to end up on all types in this blueprint.  If a type
 * would need to be produced in parallel with this blueprint but require different types or methods,
 * it must be emitted separately.
 */
public class JavaPoetFileBlueprint {

    private List<TypeSpec> types;
    private List<MethodSpec> methods;
    private List<FieldSpec> fields;
    private List<FieldSpec> inheritedFields;
    private List<FieldSpec> lensFields;

    public JavaPoetFileBlueprint() {
        this.methods = new ArrayList<>();
        this.fields = new ArrayList<>();
        this.types = new ArrayList<>();
        this.inheritedFields = new ArrayList<>();
        this.lensFields = new ArrayList<>();
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

    public void addInheritedField(FieldSpec field) {
        this.inheritedFields.add(field);
    }

    public void addLensField(FieldSpec field) {
        this.lensFields.add(field);
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

    public List<FieldSpec> getInheritedFields() {
        return inheritedFields;
    }

    public List<FieldSpec> getLensFields() {
        return lensFields;
    }
}
