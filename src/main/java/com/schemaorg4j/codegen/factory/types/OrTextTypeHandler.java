package com.schemaorg4j.codegen.factory.types;

import com.schemaorg4j.codegen.domain.SchemaDataType;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import java.util.Objects;
import java.util.Set;

public class OrTextTypeHandler extends DependsOnEmbeddedTypeResolver implements TypeHandler {

    private static final ClassName OR_TYPE = ClassName.get("com.schemaorg4j.util", "OrText");
    private final SchemaGraph graph;

    public OrTextTypeHandler(SchemaGraph graph) {
        super(graph);
        this.graph = graph;
    }

    @Override
    public boolean canHandle(SchemaProperty property) {
        Set<String> range = property.getRangeIncludesIds();
        return hasTwoTypes(range) && hasTextAsOneType(range);
    }

    private boolean hasTextAsOneType(Set<String> range) {
        return range.stream().anyMatch(id -> Objects.equals(SchemaDataType.TEXT.getId(), id));
    }

    private boolean hasTwoTypes(Set<String> range) {
        return range.size() == 2;
    }

    @Override
    public TypeName handle(SchemaProperty property) {
        Set<String> range = property.getRangeIncludesIds();
        String nonTextId = range.stream()
            .filter(id -> !Objects.equals(SchemaDataType.TEXT.getId(), id))
            .findFirst().get();

        return ParameterizedTypeName.get(OR_TYPE, resolveEmbeddedType(nonTextId));
    }
}
