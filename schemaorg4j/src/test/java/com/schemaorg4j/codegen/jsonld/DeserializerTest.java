package com.schemaorg4j.codegen.jsonld;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DeserializerTest {

    protected ObjectMapper objectMapper() {
        return Util.getObjectMapperInstnace();
    }

}
