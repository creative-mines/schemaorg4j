package com.schemaorg4j;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.schemaorg4j.parser.core.domain.DataValueList;
import com.schemaorg4j.parser.core.domain.DataValueObject;
import com.schemaorg4j.parser.core.domain.DataValueString;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

public class DataObjectDeserializer extends StdDeserializer<DataValueObject> {

    DataObjectDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public DataValueObject deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        DataValueObject dataValueObject = new DataValueObject(node.has("@type") ? node.get("@type").asText() : null);

        Iterator<String> fieldNameIterators = node.fieldNames();
        while (fieldNameIterators.hasNext()) {
            String fieldName = fieldNameIterators.next();
            if (Objects.equals(fieldName, "@type")) {
                continue;
            }

            JsonNode nextNode = node.get(fieldName);
            if (nextNode.isObject()) {
                JsonParser clone = nextNode.traverse();
                clone.setCodec(p.getCodec());
                dataValueObject.putField(fieldName, SchemaOrgJsonMapper
                    .getObjectMapper().readValue(clone, DataValueObject.class));
            } else if (nextNode.isArray()) {
                JsonParser clone = nextNode.traverse();
                clone.setCodec(p.getCodec());
                dataValueObject.putField(fieldName, SchemaOrgJsonMapper
                    .getObjectMapper().readValue(clone, DataValueList.class));
            } else {
                dataValueObject.putField(fieldName, new DataValueString(nextNode.asText()));
            }
        }

        return dataValueObject;
    }
}
