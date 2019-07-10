package com.schemaorg4j.codegen.jsonld;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import java.io.IOException;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.junit.Test;

public class GraphDeserializerTest extends DeserializerTest {

    @Test
    public void canDeserializeOneClass() throws IOException {
        String json = "{\n"
            + "  \"@graph\": [{\n"
            + "    \"@id\": \"http://schema.org/Book\",\n"
            + "    \"@type\": \"rdf:Class\",\n"
            + "    \"rdfs:label\": \"Test\",\n"
            + "    \"rdfs:comment\": \"Test\"\n"
            + "  }]\n"
            + "}";

        SchemaGraph g = objectMapper().readValue(json, SchemaGraph.class);
        assertEquals(g.getClass("http://schema.org/Book").getLabel(), "Test");
    }

    @Test
    public void canDeserializeAClassHierarchy() throws IOException {
        String json = "{\n"
            + "  \"@graph\": [{\n"
            + "    \"@id\": \"http://schema.org/Book\",\n"
            + "    \"@type\": \"rdf:Class\",\n"
            + "    \"rdfs:subClassOf\": [{\n"
            + "      \"@id\": \"http://schema.org/CreativeWork\" \n"
            + "    }]\n"
            + "  }, {\n"
            + "    \"@id\": \"http://schema.org/CreativeWork\",\n"
            + "    \"@type\": \"rdf:Class\"\n"
            + "  }]\n"
            + "}";

        SchemaGraph g = objectMapper().readValue(json, SchemaGraph.class);
        assertEquals(g.getSuperclasses("http://schema.org/Book").stream().findFirst().get().getId(),
            "http://schema.org/CreativeWork");

    }

    @Test
    public void canDeserializeAClassHierarchyOrderReversed() throws IOException {
        String json = "{\n"
            + "  \"@graph\": [{\n"
            + "    \"@id\": \"http://schema.org/CreativeWork\",\n"
            + "    \"@type\": \"rdf:Class\"\n"
            + "  },"
            + "{\n"
            + "    \"@id\": \"http://schema.org/Book\",\n"
            + "    \"@type\": \"rdf:Class\",\n"
            + "    \"rdfs:subClassOf\": [{\n"
            + "      \"@id\": \"http://schema.org/CreativeWork\" \n"
            + "    }]\n"
            + "  }]\n"
            + "}";

        SchemaGraph g = objectMapper().readValue(json, SchemaGraph.class);
        assertEquals(g.getSuperclasses("http://schema.org/Book").stream().findFirst().get().getId(),
            "http://schema.org/CreativeWork");
    }

    @Test
    public void canDeserializeMultipleInheritanceHierarchy() throws IOException {
        String json = "{\n"
            + "  \"@graph\": [{\n"
            + "    \"@id\": \"http://schema.org/CreativeWork\",\n"
            + "    \"@type\": \"rdf:Class\"\n"
            + "  },"
            + "  {\n"
            + "    \"@id\": \"http://schema.org/Book\",\n"
            + "    \"@type\": \"rdf:Class\",\n"
            + "    \"rdfs:subClassOf\": [{\n"
            + "      \"@id\": \"http://schema.org/CreativeWork\"}, \n"
            + "      {\"@id\": \"http://schema.org/Thing\" \n"
            + "    }]\n"
            + "  },\n"
            + "  {\n"
            + "    \"@id\": \"http://schema.org/Thing\",\n"
            + "    \"@type\": \"rdf:Class\"\n"
            + "  }]\n"
            + "}";

        SchemaGraph g = objectMapper().readValue(json, SchemaGraph.class);
        assertEquals(g.getSuperclasses("http://schema.org/Book").stream().map(SchemaClass::getId).collect(
            Collectors.toSet()), new HashSet<String>() {{
                add("http://schema.org/Thing");
                add("http://schema.org/CreativeWork");
            }});
    }

    @Test
    public void canDeserializePropertiesOnAClass() throws IOException {
        String json = "{ \"@graph\" : [{\n"
            + "      \"@id\": \"http://schema.org/fileFormat\",\n"
            + "      \"@type\": \"rdf:Property\",\n"
            + "      \"http://schema.org/domainIncludes\": {\n"
            + "        \"@id\": \"http://schema.org/CreativeWork\"\n"
            + "      },\n"
            + "      \"http://schema.org/rangeIncludes\": [\n"
            + "        {\n"
            + "          \"@id\": \"http://schema.org/URL\"\n"
            + "        },\n"
            + "        {\n"
            + "          \"@id\": \"http://schema.org/Text\"\n"
            + "        }\n"
            + "      ],\n"
            + "      \"http://schema.org/supersededBy\": {\n"
            + "        \"@id\": \"http://schema.org/encodingFormat\"\n"
            + "      },\n"
            + "      \"rdfs:comment\": \"Media type, typically MIME format (see <a href=\\\"http://www.iana.org/assignments/media-types/media-types.xhtml\\\">IANA site</a>) of the content e.g. application/zip of a SoftwareApplication binary. In cases where a CreativeWork has several media type representations, 'encoding' can be used to indicate each MediaObject alongside particular fileFormat information. Unregistered or niche file formats can be indicated instead via the most appropriate URL, e.g. defining Web page or a Wikipedia entry.\",\n"
            + "      \"rdfs:label\": \"fileFormat\"\n"
            + "    }]}";

        SchemaGraph g = objectMapper().readValue(json, SchemaGraph.class);
        assertEquals(g.getProperties("http://schema.org/CreativeWork").stream().map(
            SchemaProperty::getId).collect(Collectors.toSet()), new HashSet<String>() {{
                add("http://schema.org/fileFormat");
        }});
    }

    @Test
    public void canDeserializeEnumProperties() {

    }

    @Test
    public void anythingStillWaitingForASuperClassAfterFinializationWillNotShowUpInClassQueries() {

    }
}
