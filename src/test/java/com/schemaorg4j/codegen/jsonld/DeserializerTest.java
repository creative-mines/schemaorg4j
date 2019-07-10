package com.schemaorg4j.codegen.jsonld;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaDataType;
import com.schemaorg4j.codegen.domain.SchemaProperty;

public class DeserializerTest {

    protected ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        SimpleModule mod = new SimpleModule();
        mod.addDeserializer(SchemaProperty.class, new SchemaPropertyDeserializer(SchemaProperty.class));
        mod.addDeserializer(SchemaDataType.class, new SchemaDataTypeDeserializer(SchemaDataType.class));
        mod.addDeserializer(SchemaClass.class, new SchemaClassDeserializer(SchemaClass.class));
        om.registerModule(mod);
        return om;
    }

}
