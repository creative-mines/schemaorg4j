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

class DataValueListDeserializer extends StdDeserializer<DataValueList> {

    protected DataValueListDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public DataValueList deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

        JsonNode node = p.getCodec().readTree(p);

        DataValueList dataValueList = new DataValueList();

        for (int i = 0; i < node.size(); i++) {
            JsonNode nextNode = node.get(i);

            if (nextNode.isArray()) {
                JsonParser clone = nextNode.traverse();
                clone.setCodec(p.getCodec());
                dataValueList.addValue(
                    SchemaOrgJsonMapper.getObjectMapper().readValue(clone, DataValueList.class));
            } else if (nextNode.isObject()) {
                JsonParser clone = nextNode.traverse();
                clone.setCodec(p.getCodec());
                dataValueList.addValue(
                    SchemaOrgJsonMapper.getObjectMapper().readValue(clone, DataValueObject.class));
            } else {
                dataValueList.addValue(new DataValueString(nextNode.asText()));
            }
        }

        return dataValueList;
    }
}
