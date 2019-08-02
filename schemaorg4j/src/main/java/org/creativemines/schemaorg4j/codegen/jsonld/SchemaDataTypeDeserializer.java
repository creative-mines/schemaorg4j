package org.creativemines.schemaorg4j.codegen.jsonld;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.BOOLEAN_ID;
import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.DATETIME_ID;
import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.DATE_ID;
import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.FLOAT_ID;
import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.INTEGER_ID;
import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.NUMBER_ID;
import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.TEXT_ID;
import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.TIME_ID;

import java.io.IOException;

import org.creativemines.schemaorg4j.codegen.domain.SchemaDataType;

public class SchemaDataTypeDeserializer extends StdDeserializer<SchemaDataType> {

    protected SchemaDataTypeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SchemaDataType deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        switch(node.get("@id").asText()) {
            case BOOLEAN_ID:
                return SchemaDataType.BOOLEAN;
            case DATE_ID:
                return SchemaDataType.DATE;
            case DATETIME_ID:
                return SchemaDataType.DATETIME;
            case NUMBER_ID:
                return SchemaDataType.NUMBER;
            case TEXT_ID:
                return SchemaDataType.TEXT;
            case TIME_ID:
                return SchemaDataType.TIME;
            case FLOAT_ID:
                return SchemaDataType.FLOAT;
            case INTEGER_ID:
                return SchemaDataType.INTEGER;
            default:
                throw new RuntimeException("Unable to deserialize data type with @id: " + node.get("@id").asText());
        }
    }
}
