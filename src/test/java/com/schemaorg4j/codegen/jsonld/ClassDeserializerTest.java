package com.schemaorg4j.codegen.jsonld;

import static junit.framework.TestCase.assertEquals;

import com.schemaorg4j.codegen.domain.SchemaClass;
import java.io.IOException;
import java.util.HashSet;
import org.junit.Test;

public class ClassDeserializerTest extends DeserializerTest {

    public String classJson() {
        return "{\n"
            + "      \"@id\": \"http://schema.org/PaymentCard\",\n"
            + "      \"@type\": \"rdfs:Class\",\n"
            + "      \"http://purl.org/dc/terms/source\": {\n"
            + "        \"@id\": \"http://www.w3.org/wiki/WebSchemas/SchemaDotOrgSources#FIBO\"\n"
            + "      },\n"
            + "      \"rdfs:comment\": \"A payment method using a credit, debit, store or other card to associate the payment with an account.\",\n"
            + "      \"rdfs:label\": \"PaymentCard\",\n"
            + "      \"rdfs:subClassOf\": [\n"
            + "        {\n"
            + "          \"@id\": \"http://schema.org/FinancialProduct\"\n"
            + "        },\n"
            + "        {\n"
            + "          \"@id\": \"http://schema.org/PaymentMethod\"\n"
            + "        }\n"
            + "      ]\n"
            + "    },";
    }

    private String withOnlyOneSubclassOfEntry() {
        return "{\n"
            + "      \"@id\": \"http://schema.org/PaymentCard\",\n"
            + "      \"@type\": \"rdfs:Class\",\n"
            + "      \"http://purl.org/dc/terms/source\": {\n"
            + "        \"@id\": \"http://www.w3.org/wiki/WebSchemas/SchemaDotOrgSources#FIBO\"\n"
            + "      },\n"
            + "      \"rdfs:comment\": \"A payment method using a credit, debit, store or other card to associate the payment with an account.\",\n"
            + "      \"rdfs:label\": \"PaymentCard\",\n"
            + "      \"rdfs:subClassOf\": [\n"
            + "        {\n"
            + "          \"@id\": \"http://schema.org/PaymentMethod\"\n"
            + "        }\n"
            + "      ]\n"
            + "    },";
    }

    @Test
    public void testClassDeserializesId() throws IOException {
        SchemaClass sc = objectMapper().readValue(classJson(), SchemaClass.class);
        assertEquals(sc.getId(), "http://schema.org/PaymentCard");
    }

    @Test
    public void testClassDeserializesSubclassSet() throws IOException {
        SchemaClass sc = objectMapper().readValue(classJson(), SchemaClass.class);
        assertEquals(sc.getSubclassOfIds(), new HashSet<String>() {{
            add("http://schema.org/FinancialProduct");
            add("http://schema.org/PaymentMethod");
        }});
    }

    @Test
    public void testClassDeserializesLabel() throws IOException {
        SchemaClass sc = objectMapper().readValue(classJson(), SchemaClass.class);
        assertEquals(sc.getLabel(), "PaymentCard");
    }

    @Test
    public void testClassDeserializesComment() throws IOException {
        SchemaClass sc = objectMapper().readValue(classJson(), SchemaClass.class);
        assertEquals(sc.getComment(), "A payment method using a credit, debit, store or other card to associate the payment with an account.");
    }

    @Test
    public void testClassCanDeserializeSubclassSetWhenThereIsOnlyOneEntry() throws IOException {
        SchemaClass sc = objectMapper().readValue(withOnlyOneSubclassOfEntry(), SchemaClass.class);
        assertEquals(sc.getSubclassOfIds(), new HashSet<String>() {{
            add("http://schema.org/PaymentMethod");
        }});
    }

}
