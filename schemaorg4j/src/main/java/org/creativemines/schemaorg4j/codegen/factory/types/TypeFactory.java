package org.creativemines.schemaorg4j.codegen.factory.types;

import org.creativemines.schemaorg4j.codegen.domain.SchemaProperty;

import com.squareup.javapoet.TypeName;

public interface TypeFactory {

    FieldDeclarationRequirement build(SchemaProperty property);

}
