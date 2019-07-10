package com.schemaorg4j.codegen.jsonld;

import static org.junit.Assert.assertEquals;

import com.schemaorg4j.codegen.domain.SchemaEnumMember;
import java.io.IOException;
import org.junit.Test;

public class EnumDeserializerTest extends DeserializerTest {

    private String enumPropertyJson() {
        return "{\n"
            + "      \"@id\": \"http://schema.org/AudiobookFormat\",\n"
            + "      \"@type\": \"http://schema.org/BookFormatType\",\n"
            + "      \"rdfs:comment\": \"Book format: Audiobook. This is an enumerated value for use with the bookFormat property. There is also a type 'Audiobook' in the bib extension which includes Audiobook specific properties.\",\n"
            + "      \"rdfs:label\": \"AudiobookFormat\"\n"
            + "    }";
    }

    @Test
    public void idIsDeserializedCorrectly() throws IOException {
        SchemaEnumMember member = objectMapper().readValue(enumPropertyJson(), SchemaEnumMember.class);
        assertEquals(member.getId(), "http://schema.org/AudiobookFormat");
    }


    @Test
    public void parentEnumIdIsDeserializedCorrectly() throws IOException {
        SchemaEnumMember member = objectMapper().readValue(enumPropertyJson(), SchemaEnumMember.class);
        assertEquals(member.getEnumId(), "http://schema.org/BookFormatType");
    }

    @Test
    public void labelIsDeserializedCorrectly() throws IOException {
        SchemaEnumMember member = objectMapper().readValue(enumPropertyJson(), SchemaEnumMember.class);
        assertEquals(member.getLabel(), "AudiobookFormat");
    }
}
