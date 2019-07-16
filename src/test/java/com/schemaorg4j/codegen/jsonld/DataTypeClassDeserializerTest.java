package com.schemaorg4j.codegen.jsonld;

import static org.junit.Assert.assertEquals;

import com.schemaorg4j.codegen.domain.SchemaDataType;
import java.io.IOException;
import org.junit.Test;

public class DataTypeClassDeserializerTest extends DeserializerTest {

    @Test
    public void testBooleanMapsToCorrectClass() throws IOException {
        String json = "{\n"
            + "      \"@id\": \"http://schema.org/Boolean\",\n"
            + "      \"@type\": [\n"
            + "        \"rdfs:Class\",\n"
            + "        \"http://schema.org/DataType\"\n"
            + "      ],\n"
            + "      \"rdfs:comment\": \"Boolean: True or False.\",\n"
            + "      \"rdfs:label\": \"Boolean\"\n"
            + "    }";

        SchemaDataType dt = objectMapper().readValue(json, SchemaDataType.class);
        assertEquals(dt.getId(), "http://schema.org/Boolean");
        assertEquals(dt.getJavaDataType(), "Boolean");
    }

    @Test
    public void testDateMapsToCorrectClass() throws IOException {
        String json = "{\n"
            + "      \"@id\": \"http://schema.org/Date\",\n"
            + "      \"@type\": [\n"
            + "        \"rdfs:Class\",\n"
            + "        \"http://schema.org/DataType\"\n"
            + "      ]\n"
            + "    }";

        SchemaDataType dt = objectMapper().readValue(json, SchemaDataType.class);
        assertEquals(dt.getId(), "http://schema.org/Date");
        assertEquals(dt.getJavaDataType(), "LocalDate");

    }

    @Test
    public void testDateTimeMapsToCorrectClass() throws IOException {
        String json = "{\n"
            + "      \"@id\": \"http://schema.org/DateTime\",\n"
            + "      \"@type\": [\n"
            + "        \"rdfs:Class\",\n"
            + "        \"http://schema.org/DataType\"\n"
            + "      ]\n"
            + "    }";

        SchemaDataType dt = objectMapper().readValue(json, SchemaDataType.class);
        assertEquals(dt.getId(), "http://schema.org/DateTime");
        assertEquals(dt.getJavaDataType(), "ZonedDateTime");

    }

    @Test
    public void testNumberMapsToCorrectClass() throws IOException {

        String json = "{\n"
            + "      \"@id\": \"http://schema.org/Number\",\n"
            + "      \"@type\": [\n"
            + "        \"rdfs:Class\",\n"
            + "        \"http://schema.org/DataType\"\n"
            + "      ]\n"
            + "    }";

        SchemaDataType dt = objectMapper().readValue(json, SchemaDataType.class);
        assertEquals(dt.getId(), "http://schema.org/Number");
        assertEquals(dt.getJavaDataType(), "Float");
    }

    @Test
    public void testIntegerMapsToCorrectClass() throws IOException {
        String json = "{\n"
            + "      \"@id\": \"http://schema.org/Integer\",\n"
            + "      \"@type\": [\n"
            + "        \"rdfs:Class\",\n"
            + "        \"http://schema.org/DataType\"\n"
            + "      ]\n"
            + "    }";

        SchemaDataType dt = objectMapper().readValue(json, SchemaDataType.class);
        assertEquals(dt.getId(), "http://schema.org/Integer");
        assertEquals(dt.getJavaDataType(), "Integer");

    }

    @Test
    public void testFloatMapsToCorrectClass() throws IOException {

        String json = "{\n"
            + "      \"@id\": \"http://schema.org/Float\",\n"
            + "      \"@type\": [\n"
            + "        \"rdfs:Class\",\n"
            + "        \"http://schema.org/DataType\"\n"
            + "      ]\n"
            + "    }";

        SchemaDataType dt = objectMapper().readValue(json, SchemaDataType.class);
        assertEquals(dt.getId(), "http://schema.org/Float");
        assertEquals(dt.getJavaDataType(), "Float");
    }

    @Test
    public void testStringMapsToCorrectClass() throws IOException {

        String json = "{\n"
            + "      \"@id\": \"http://schema.org/Text\",\n"
            + "      \"@type\": [\n"
            + "        \"rdfs:Class\",\n"
            + "        \"http://schema.org/DataType\"\n"
            + "      ]\n"
            + "    }";

        SchemaDataType dt = objectMapper().readValue(json, SchemaDataType.class);
        assertEquals(dt.getId(), "http://schema.org/Text");
        assertEquals(dt.getJavaDataType(), "String");
    }

    @Test
    public void testTimeMapsToCorrectClass() throws IOException {
        String json = "{\n"
            + "      \"@id\": \"http://schema.org/Time\",\n"
            + "      \"@type\": [\n"
            + "        \"rdfs:Class\",\n"
            + "        \"http://schema.org/DataType\"\n"
            + "      ]\n"
            + "    }";

        SchemaDataType dt = objectMapper().readValue(json, SchemaDataType.class);
        assertEquals(dt.getId(), "http://schema.org/Time");
        assertEquals(dt.getJavaDataType(), "LocalTime");

    }
}
