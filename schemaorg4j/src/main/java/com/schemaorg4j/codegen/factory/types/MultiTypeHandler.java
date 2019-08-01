package com.schemaorg4j.codegen.factory.types;

import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.COMBO_TYPE_PACKAGE;

import com.schemaorg4j.codegen.domain.SchemaDataType;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import java.util.stream.Collectors;

public class MultiTypeHandler implements TypeHandler {

    private final SchemaGraph graph;

    public MultiTypeHandler(SchemaGraph graph) {
        this.graph = graph;
    }

    @Override
    public boolean canHandle(SchemaProperty property) {
        return property.getRangeIncludesIds().stream()
            .allMatch(id -> graph.getClass(id) != null || SchemaDataType.findById(id).isPresent());
    }

    @Override
    public FieldDeclarationRequirement handle(SchemaProperty property) {
        String name = property.getRangeIncludesIds()
            .stream()
            .map(id -> {
                if (graph.getClass(id) != null) {
                    return graph.getClass(id).getLabel();
                } else {
                    return SchemaDataType.findById(id).get().getLabel();
                }
            }).sorted()
            .collect(Collectors.joining("Or"));

        return new FieldDeclarationRequirement(ClassName.get(COMBO_TYPE_PACKAGE, name));
    }
}
