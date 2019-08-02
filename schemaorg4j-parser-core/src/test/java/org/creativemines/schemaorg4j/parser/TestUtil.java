package org.creativemines.schemaorg4j.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

import org.creativemines.schemaorg4j.parser.core.domain.DataValueList;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueObject;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueString;

public class TestUtil {

    private static ObjectMapper OBJECT_MAPPER;

    private static ObjectMapper getObjectMapper() {
        if (OBJECT_MAPPER == null) {
            OBJECT_MAPPER = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(DataValueObject.class, new DataObjectDeserializer(DataValueObject.class));
            module.addDeserializer(DataValueList.class, new DataValueListDeserializer(DataValueList.class));
            OBJECT_MAPPER.registerModule(module);
        }
        return OBJECT_MAPPER;
    }

    public static DataValueObject fromJson(String json) throws IOException {
        return getObjectMapper().readValue(json, DataValueObject.class);
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return getObjectMapper().readValue(json, clazz);
    }

    private static class DataObjectDeserializer extends StdDeserializer<DataValueObject> {

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
                    dataValueObject.putField(fieldName, OBJECT_MAPPER.readValue(clone, DataValueObject.class));
                } else if (nextNode.isArray()) {
                    JsonParser clone = nextNode.traverse();
                    clone.setCodec(p.getCodec());
                    dataValueObject.putField(fieldName, OBJECT_MAPPER.readValue(clone, DataValueList.class));
                } else {
                    dataValueObject.putField(fieldName, new DataValueString(nextNode.asText()));
                }
            }

            return dataValueObject;
        }
    }

    private static class DataValueListDeserializer extends StdDeserializer<DataValueList> {

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
                    dataValueList.addValue(OBJECT_MAPPER.readValue(clone, DataValueList.class));
                } else if (nextNode.isObject()) {
                    JsonParser clone = nextNode.traverse();
                    clone.setCodec(p.getCodec());
                    dataValueList.addValue(OBJECT_MAPPER.readValue(clone, DataValueObject.class));
                } else {
                    dataValueList.addValue(new DataValueString(nextNode.asText()));
                }
            }

            return dataValueList;
        }
    }
}
