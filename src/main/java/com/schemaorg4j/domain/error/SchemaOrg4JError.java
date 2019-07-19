package com.schemaorg4j.domain.error;

/**
 * Error object for storing data about fields or objects which could not be interpreted as
 * proper Schema.org objects.  Contents can be any Object.
 *
 * Example from the schemaorg4j-parser-core project:
 * <pre>
 *
 *     DataValueObject dataValueObject = fromJson("{\n"
 *         + "  \"@context\": \"http://schema.org\",\n"
 *         + "  \"@type\": \"MusicEvent\",\n"
 *         + "  \"location\": 7\n"
 *         + "}");
 *
 *     MusicEvent event = (MusicEvent) new SchemaMapper().map(dataValueObject);
 *     SchemaOrg4JError error = event.getSchemaOrg4JErrors().get(0);
 *
 *     assertNotNull(error.getMessage());
 *     assertEquals(
 *         ((DataValueString) (
 *             (DataValueObject) error.getContents()).getValueFor("location")
 *         ).asString(), "7");
 * </pre>
 */
public class SchemaOrg4JError {

    private final Object contents;
    private final String message;

    public SchemaOrg4JError(String message, Object contents) {
        this.message = message;
        this.contents = contents;
    }

    public Object getContents() {
        return contents;
    }

    public String getMessage() {
        return message;
    }
}
