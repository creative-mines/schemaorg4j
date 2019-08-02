package org.creativemines.schemaorg4j.codegen.jsonld;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashSet;

import org.creativemines.schemaorg4j.codegen.domain.SchemaProperty;
import org.junit.Test;

public class PropertyDeserializerTest extends DeserializerTest {

    private String property() {
        return "{\n"
            + "      \"@id\": \"http://schema.org/awayTeam\",\n"
            + "      \"@type\": \"rdf:Property\",\n"
            + "      \"http://schema.org/domainIncludes\": {\n"
            + "        \"@id\": \"http://schema.org/SportsEvent\"\n"
            + "      },\n"
            + "      \"http://schema.org/rangeIncludes\": [\n"
            + "        {\n"
            + "          \"@id\": \"http://schema.org/SportsTeam\"\n"
            + "        },\n"
            + "        {\n"
            + "          \"@id\": \"http://schema.org/Person\"\n"
            + "        }\n"
            + "      ],\n"
            + "      \"rdfs:comment\": \"The away team in a sports event.\",\n"
            + "      \"rdfs:label\": \"awayTeam\",\n"
            + "      \"rdfs:subPropertyOf\": {\n"
            + "        \"@id\": \"http://schema.org/competitor\"\n"
            + "      }\n"
            + "    },";
    }

    @Test
    public void testPropertyDeserializesId() throws IOException {
        SchemaProperty sp = objectMapper().readValue(property(), SchemaProperty.class);
        assertEquals(sp.getId(), "http://schema.org/awayTeam");
    }

    @Test
    public void testPropertyDeserializesRangeIncludes() throws IOException {
        SchemaProperty sp = objectMapper().readValue(property(), SchemaProperty.class);
        assertEquals(sp.getRangeIncludesIds(), new HashSet<String>() {{
            add("http://schema.org/SportsTeam");
            add("http://schema.org/Person");
        }});
    }

    @Test
    public void testPropertyDeserializesDomainIncludes() throws IOException {
        SchemaProperty sp = objectMapper().readValue(property(), SchemaProperty.class);
        assertEquals(sp.getDomainIncludesIds(), new HashSet<String>() {{
            add("http://schema.org/SportsEvent");
        }});
    }

    @Test
    public void testPropertyDeserializesComment() throws IOException {
        SchemaProperty sp = objectMapper().readValue(property(), SchemaProperty.class);
        assertEquals(sp.getComment(), "The away team in a sports event.");
    }

    @Test
    public void testPropertyDeserializesLabel() throws IOException {
        SchemaProperty sp = objectMapper().readValue(property(), SchemaProperty.class);
        assertEquals(sp.getLabel(), "awayTeam");
    }
}
