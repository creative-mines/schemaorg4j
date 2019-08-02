package org.creativemines.schemaorg4j.codegen.feature;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import java.util.function.Consumer;

public class FeatureResponse {

    private final Iterable<FieldSpec> fields;
    private final Iterable<MethodSpec> methods;

    public FeatureResponse(Iterable<FieldSpec> fields, Iterable<MethodSpec> methods) {
        this.fields = fields;
        this.methods = methods;
    }

    public void handle(Consumer<FieldSpec> fieldHandler, Consumer<MethodSpec> methodHandler) {
        this.fields.forEach(fieldHandler);
        this.methods.forEach(methodHandler);
    }

}
