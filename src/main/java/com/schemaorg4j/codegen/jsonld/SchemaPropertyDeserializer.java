package com.schemaorg4j.codegen.jsonld;

import static com.schemaorg4j.codegen.jsonld.SchemaOrgConstants.COMMENT;
import static com.schemaorg4j.codegen.jsonld.SchemaOrgConstants.DOMAIN_INCLUDES;
import static com.schemaorg4j.codegen.jsonld.SchemaOrgConstants.ID;
import static com.schemaorg4j.codegen.jsonld.SchemaOrgConstants.LABEL;
import static com.schemaorg4j.codegen.jsonld.SchemaOrgConstants.RANGE_INCLUDES;
import static com.schemaorg4j.codegen.jsonld.Util.extractAsSingleFieldOrArray;
import static com.schemaorg4j.codegen.jsonld.Util.getComment;
import static com.schemaorg4j.codegen.jsonld.Util.getLabel;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.schemaorg4j.codegen.domain.SchemaPropertyBuilder;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SchemaPropertyDeserializer extends StdDeserializer<SchemaProperty> {

    protected SchemaPropertyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SchemaProperty deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        Set<String> domainIncludes = getDomainIncludes(node);
        Set<String> rangeIncludes = getRangeIncludes(node);

        return new SchemaPropertyBuilder()
            .setId(node.get(ID).asText())
            .setDomainIncludesIds(domainIncludes)
            .setRangeIncludesIds(rangeIncludes)
            .setLabel(getLabel(node))
            .setComment(getComment(node))
            .createSchemaProperty();
    }

    private Set<String> getRangeIncludes(JsonNode node) {
        return extractAsSingleFieldOrArray(node, RANGE_INCLUDES);
    }

    private Set<String> getDomainIncludes(JsonNode node) {
        return extractAsSingleFieldOrArray(node, DOMAIN_INCLUDES);
    }

}
