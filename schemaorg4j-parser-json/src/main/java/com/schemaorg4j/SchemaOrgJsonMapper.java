package com.schemaorg4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.schemaorg4j.parser.core.SchemaMapper;
import com.schemaorg4j.parser.core.domain.DataValueList;
import com.schemaorg4j.parser.core.domain.DataValueObject;
import java.io.IOException;

public class SchemaOrgJsonMapper {

    private static ObjectMapper OBJECT_MAPPER;

    private final SchemaMapper schemaMapper;

    public SchemaOrgJsonMapper() {
        this.schemaMapper = new SchemaMapper();
    }

    public DataValueObject map(String contents) throws IOException {
        return getObjectMapper().readValue(contents, DataValueObject.class);
    }

    public DataValueList mapList(String contents) throws IOException {
        return getObjectMapper().readValue(contents, DataValueList.class);
    }

    static ObjectMapper getObjectMapper() {
        if (OBJECT_MAPPER == null) {
            OBJECT_MAPPER = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(DataValueObject.class,
                new DataObjectDeserializer(DataValueObject.class));
            module.addDeserializer(DataValueList.class,
                new DataValueListDeserializer(DataValueList.class));
            OBJECT_MAPPER.registerModule(module);
        }

        return OBJECT_MAPPER;
    }
}

