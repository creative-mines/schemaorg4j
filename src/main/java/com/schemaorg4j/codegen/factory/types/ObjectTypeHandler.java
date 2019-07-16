package com.schemaorg4j.codegen.factory.types;

import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.DOMAIN_PACKAGE;

import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import java.util.Optional;

public class ObjectTypeHandler implements TypeHandler {

    private SchemaGraph graph;

    public ObjectTypeHandler(SchemaGraph graph) {
        this.graph = graph;
    }

    @Override
    public boolean canHandle(SchemaProperty property) {
        return property.getRangeIncludesIds().size() == 1
            && graph.getClass(property.getRangeIncludesIds().stream().findFirst().get()) != null;
    }

    @Override
    public FieldDeclarationRequirement handle(SchemaProperty property) {
        Optional<String> only = property.getRangeIncludesIds().stream().findFirst();
        return only.map(typeId -> {
            String className = graph.getClass(typeId).getLabel();
            return new FieldDeclarationRequirement(ClassName.get(DOMAIN_PACKAGE, className));
        }).orElseThrow(() ->
            new UnsupportedOperationException("Should not have handled " + property.getId()));
    }
}
