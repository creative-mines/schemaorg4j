package org.creativemines.schemaorg4j.codegen.factory.types;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.creativemines.schemaorg4j.SchemaOrg4JConstants;
import org.creativemines.schemaorg4j.annotations.SchemaOrg4JOrType;
import org.creativemines.schemaorg4j.codegen.domain.SchemaDataType;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.domain.SchemaProperty;

public class OrTextTypeHandler extends DependsOnEmbeddedTypeResolver implements TypeHandler {

    private static final ClassName OR_TYPE = ClassName.get(SchemaOrg4JConstants.UTIL_PACKAGE, "OrText");
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
    public FieldDeclarationRequirement handle(SchemaProperty property) {
        Optional<String> maybeNonTextId = getNonTextId(property);

        if (!maybeNonTextId.isPresent()) {
            return new FieldDeclarationRequirement(ClassName.get("java.lang", "String"));
        }

        String nonTextId = maybeNonTextId.get();
        FieldDeclarationRequirement nonTextType = resolveEmbeddedType(nonTextId);

        AnnotationSpec annotation = AnnotationSpec.builder(SchemaOrg4JOrType.class)
            .addMember("value", "$T.class", nonTextType.getTypeName()).build();

        return new FieldDeclarationRequirement(
            ParameterizedTypeName.get(OR_TYPE, nonTextType.getTypeName()), annotation);
    }

    public Optional<String> getNonTextId(SchemaProperty property) {
        Set<String> range = property.getRangeIncludesIds();
        return range.stream()
            .filter(id -> !Objects
                .equals(SchemaDataType.TEXT.getId(), id))
            .findFirst();
    }
}
