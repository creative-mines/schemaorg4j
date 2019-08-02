package org.creativemines.schemaorg4j.codegen.jsonld;

import org.creativemines.schemaorg4j.codegen.jsonld.Util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DeserializerTest {

    protected ObjectMapper objectMapper() {
        return Util.getObjectMapperInstnace();
    }

}
