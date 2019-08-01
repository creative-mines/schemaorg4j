package com.schemaorg4j.parser;

import static com.schemaorg4j.parser.TestUtil.fromJson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.schemaorg4j.domain.Person;
import com.schemaorg4j.parser.core.SchemaHandler;
import com.schemaorg4j.parser.core.SchemaMapper;
import com.schemaorg4j.parser.core.domain.DataValueList;
import com.schemaorg4j.parser.core.domain.DataValueObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;

public class SchemaHandlerTest {

    @Test
    public void canHandleInstancesOfAClass() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"name\": \"Shostakovich Leningrad\",\n"
            + "  \"performer\": [\n"
            + "    {\n"
            + "      \"@type\": \"MusicGroup\",\n"
            + "      \"name\": \"Chicago Symphony Orchestra\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"@type\": \"Person\",\n"
            + "      \"name\": \"Jaap van Zweden\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"@type\": \"Person\",\n"
            + "      \"name\": \"Bach\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"@type\": \"Person\",\n"
            + "      \"name\": \"Godel\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"@type\": \"Person\",\n"
            + "      \"name\": \"Escher\"\n"
            + "    }\n"
            + "  ]\n"
            + "}");

        List<Person> people = new ArrayList<>();

        SchemaHandler handler = new SchemaHandler(new SchemaMapper());
        handler.addHandler(Person.class, people::add).consume(dataValueObject);

        Set<String> nameSet = people.stream().map(Person::getName).collect(Collectors.toSet());
        assertTrue(nameSet.contains("Jaap van Zweden"));
        assertTrue(nameSet.contains("Bach"));
        assertTrue(nameSet.contains("Godel"));
        assertTrue(nameSet.contains("Escher"));
    }

    @Test
    public void canHandleParameterizedOrTypes() {

    }

    @Test
    public void canHandleListOfEntries() throws IOException {
        DataValueList dataValueList = fromJson("[{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"name\": \"Shostakovich Leningrad\",\n"
            + "  \"performer\": [\n"
            + "    {\n"
            + "      \"@type\": \"MusicGroup\",\n"
            + "      \"name\": \"Chicago Symphony Orchestra\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"@type\": \"Person\",\n"
            + "      \"name\": \"Jaap van Zweden\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"@type\": \"Person\",\n"
            + "      \"name\": \"Bach\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"@type\": \"Person\",\n"
            + "      \"name\": \"Godel\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"@type\": \"Person\",\n"
            + "      \"name\": \"Escher\"\n"
            + "    }\n"
            + "  ]\n"
            + "}]", DataValueList.class);

        List<Person> people = new ArrayList<>();

        SchemaHandler handler = new SchemaHandler(new SchemaMapper());
        handler.addHandler(Person.class, people::add).consume(dataValueList);

        Set<String> nameSet = people.stream().map(Person::getName).collect(Collectors.toSet());
        assertTrue(nameSet.contains("Jaap van Zweden"));
        assertTrue(nameSet.contains("Bach"));
        assertTrue(nameSet.contains("Godel"));
        assertTrue(nameSet.contains("Escher"));
    }
}
