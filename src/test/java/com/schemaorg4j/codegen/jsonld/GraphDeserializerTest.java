package com.schemaorg4j.codegen.jsonld;

import static org.junit.Assert.assertEquals;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaGraph;
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
    public void canDeserializePropertiesOnAClass() {

    }

    @Test
    public void canDeserializeEnumProperties() {

    }

}
