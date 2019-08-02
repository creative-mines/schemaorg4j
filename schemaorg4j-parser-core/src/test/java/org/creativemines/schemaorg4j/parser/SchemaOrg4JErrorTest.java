package org.creativemines.schemaorg4j.parser;

import static org.creativemines.schemaorg4j.parser.TestUtil.fromJson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.creativemines.schemaorg4j.domain.Book;
import org.creativemines.schemaorg4j.domain.MusicEvent;
import org.creativemines.schemaorg4j.domain.error.SchemaOrg4JError;
import org.creativemines.schemaorg4j.parser.core.SchemaMapper;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueObject;
import org.junit.Test;

public class SchemaOrg4JErrorTest {

    @Test
    public void totalGarbageInputWillOutputAsAnErrorObject() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"name\": {\"someField\": \"test\"}\n"
            + "}");

        MusicEvent event = (MusicEvent) new SchemaMapper().map(dataValueObject);
        SchemaOrg4JError error = event.getSchemaOrg4JErrors().get(0);

        assertNotNull(error.getMessage());
        assertNotNull(error.getContents());
    }

    @Test
    public void fieldSuppliedAnObjectWillOutputAsAnErrorObject() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"doorTime\": {\"@type\": \"Person\", \"award\": \"test\"}\n"
            + "}");

        MusicEvent event = (MusicEvent) new SchemaMapper().map(dataValueObject);
        SchemaOrg4JError error = event.getSchemaOrg4JErrors().get(0);

        assertNotNull(error.getMessage());
        assertNotNull(error.getContents());
    }

    @Test
    public void fieldOfMismatchedPrimitiveTypeWillOutputAsAnErrorObject() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"doorTime\": \"7.0\"\n"
            + "}");

        MusicEvent event = (MusicEvent) new SchemaMapper().map(dataValueObject);
        SchemaOrg4JError error = event.getSchemaOrg4JErrors().get(0);

        assertNotNull(error.getMessage());
        assertNotNull(error.getContents());
    }

    @Test
    public void fieldSuppliedAListWithWrongListTypesWillOutputInErrorObject() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"remainingAttendeeCapacity\": [{\"@type\": \"Person\", \"award\": \"test\"}]\n"
            + "}");

        MusicEvent event = (MusicEvent) new SchemaMapper().map(dataValueObject);
        SchemaOrg4JError error = event.getSchemaOrg4JErrors().get(0);

        assertNotNull(error.getMessage());
        assertNotNull(error.getContents());
    }

    @Test
    public void fieldSuppliedWithBothCorrectAndIncorrectListValuesWillStillHaveCorrectValues()
        throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"remainingAttendeeCapacity\": [\"7\", {\"@type\": \"Person\", \"award\": \"test\"}]\n"
            + "}");

        MusicEvent event = (MusicEvent) new SchemaMapper().map(dataValueObject);
        assertEquals(event.getRemainingAttendeeCapacity().intValue(), 7);

        SchemaOrg4JError error = event.getRemainingAttendeeCapacityData().getSchemaOrg4JErrors()
            .get(0);

        assertNotNull(error.getMessage());
        assertNotNull(error.getContents());
    }


    @Test
    public void objectSuppliedPrimitiveWillNotOutputInErrorObject() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"actor\": 7\n"
            + "}");

        MusicEvent event = (MusicEvent) new SchemaMapper().map(dataValueObject);
        assertEquals(event.getActor().getSimpleValue(), "7");
        assertTrue(event.getSchemaOrg4JErrors().isEmpty());
    }

    @Test
    public void objectSuppliedWrongTypeWillOutputInErrorObject() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"actor\": {\"@type\": \"Book\"}\n"
            + "}");

        MusicEvent event = (MusicEvent) new SchemaMapper().map(dataValueObject);
        SchemaOrg4JError error = event.getSchemaOrg4JErrors().get(0);

        assertNotNull(error.getMessage());
        assertNotNull(error.getContents());
    }

    @Test
    public void objectSuppliedWrongListTypeWillOutputInErrorObject() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"actor\": [{\"@type\": \"Person\"}, {\"@type\": \"Book\"}]\n"
            + "}");

        MusicEvent event = (MusicEvent) new SchemaMapper().map(dataValueObject);
        SchemaOrg4JError error = event.getActor().getSchemaOrg4JErrors().get(0);

        assertNotNull(error.getMessage());
        assertNotNull(error.getContents());

    }

    @Test
    public void listOfListWillOutputInErrorObject() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"actor\": [[{\"@type\": \"Person\"}, {\"@type\": \"Book\"}]]\n"
            + "}");

        MusicEvent event = (MusicEvent) new SchemaMapper().map(dataValueObject);
        SchemaOrg4JError error = event.getSchemaOrg4JErrors().get(0);

        assertNotNull(error.getMessage());
        assertNotNull(error.getContents());
    }

    @Test
    public void orTextSuppliedObjectTypeWillOutputInErrorObject() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"inLanguage\": {\"@type\": \"Person\"}\n"
            + "}");

        MusicEvent event = (MusicEvent) new SchemaMapper().map(dataValueObject);
        SchemaOrg4JError error = event.getSchemaOrg4JErrors().get(0);

        assertNotNull(error.getMessage());
        assertNotNull(error.getContents());
    }

    @Test
    public void orTextSuppliedListTypeWithWrongOrsWillOutputInErrorObject() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"inLanguage\": [{\"@type\": \"Person\"}]\n"
            + "}");

        MusicEvent event = (MusicEvent) new SchemaMapper().map(dataValueObject);
        SchemaOrg4JError error = event.getSchemaOrg4JErrors().get(0);

        assertNotNull(error.getMessage());
        assertNotNull(error.getContents());
    }

    @Test
    public void complexOrTypeSuppliedWrongTypeWillOutputInErrorObject() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"location\": {\"@type\": \"Person\"}\n"
            + "}");

        MusicEvent event = (MusicEvent) new SchemaMapper().map(dataValueObject);
        SchemaOrg4JError error = event.getSchemaOrg4JErrors().get(0);

        assertNotNull(error.getMessage());
        assertEquals(
            ((DataValueObject) (
                (DataValueObject) error.getContents()).getValueFor("location")
            ).getType(), "Person");
    }

    @Test
    public void complexOrTypeWithWrongTypesInListWillOutputInErrorObject() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"location\": [{\"@type\": \"Place\"}, \"Hello\", {\"@type\": \"Book\"}]\n"
            + "}");

        MusicEvent event = (MusicEvent) new SchemaMapper().map(dataValueObject);
        assertNotNull(event.getLocation());
        SchemaOrg4JError error = event.getLocation().getSchemaOrg4JErrors().get(0);

        assertNotNull(error.getMessage());
        assertNotNull(error.getContents());
    }

    @Test
    public void enumsDeserializedAsAnArrayWillError() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"Book\",\n"
            + "  \"bookFormat\": [{\"@type\": \"AudiobookFormat\"}]\n"
            + "}");

        Book book = (Book) new SchemaMapper().map(dataValueObject);
        assertNotNull(book.getSchemaOrg4JErrors().get(0));
    }

    @Test
    public void enumsDeserializedAsAnObjectWillError() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"Book\",\n"
            + "  \"bookFormat\": [{\"@type\": \"AudiobookFormat\"}]\n"
            + "}");

        Book book = (Book) new SchemaMapper().map(dataValueObject);
        assertNotNull(book.getSchemaOrg4JErrors().get(0));
    }

    @Test
    public void enumsDeserializedAsNoMatchWillError() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"Book\",\n"
            + "  \"bookFormat\": \"noMeaning\"\n"
            + "}");

        Book book = (Book) new SchemaMapper().map(dataValueObject);
        assertNotNull(book.getSchemaOrg4JErrors().get(0));
    }
}
