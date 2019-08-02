package org.creativemines.schemaorg4j.codegen.factory.types;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.COMBO_TYPE_PACKAGE;

import java.util.stream.Collectors;

import org.creativemines.schemaorg4j.codegen.domain.SchemaDataType;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.domain.SchemaProperty;

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
