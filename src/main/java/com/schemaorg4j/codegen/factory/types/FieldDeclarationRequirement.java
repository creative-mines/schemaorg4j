package com.schemaorg4j.codegen.factory.types;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.TypeName;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class FieldDeclarationRequirement {

    private TypeName typeName;
    private Collection<AnnotationSpec> fieldAnnotations;

    public FieldDeclarationRequirement(TypeName typeName) {
        this.typeName = typeName;
        this.fieldAnnotations = Collections.emptyList();
    }

    public FieldDeclarationRequirement(TypeName typeName, AnnotationSpec ... annotationSpecs) {
        this.typeName = typeName;
        this.fieldAnnotations = Arrays.asList(annotationSpecs);
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public Iterable<AnnotationSpec> getFieldAnnotations() {
        return fieldAnnotations;
    }
}
