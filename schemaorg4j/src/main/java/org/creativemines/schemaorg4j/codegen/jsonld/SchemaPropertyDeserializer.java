package org.creativemines.schemaorg4j.codegen.jsonld;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.DOMAIN_INCLUDES;
import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.ID;
import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.RANGE_INCLUDES;
import static org.creativemines.schemaorg4j.codegen.jsonld.Util.extractAsSingleFieldOrArray;
import static org.creativemines.schemaorg4j.codegen.jsonld.Util.getComment;
import static org.creativemines.schemaorg4j.codegen.jsonld.Util.getLabel;

import java.io.IOException;
import java.util.Set;

import org.creativemines.schemaorg4j.codegen.domain.SchemaProperty;
import org.creativemines.schemaorg4j.codegen.domain.SchemaPropertyBuilder;

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
