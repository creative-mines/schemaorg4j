package com.schemaorg4j.codegen.factory.types;

import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.squareup.javapoet.TypeName;

public interface TypeHandler {

    boolean canHandle(SchemaProperty property);

    TypeName handle(SchemaProperty property);
}
