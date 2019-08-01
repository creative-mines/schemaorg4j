package com.schemaorg4j.codegen.jsonld;

import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.LABEL;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.TYPE;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.schemaorg4j.codegen.domain.SchemaEnumMember;
import java.io.IOException;

public class SchemaEnumMemberDeserializer extends StdDeserializer<SchemaEnumMember> {

    protected SchemaEnumMemberDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SchemaEnumMember deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        return new SchemaEnumMember(node.get(ID).asText(), node.get(TYPE).asText(),
            node.get(LABEL).asText());
    }
}
