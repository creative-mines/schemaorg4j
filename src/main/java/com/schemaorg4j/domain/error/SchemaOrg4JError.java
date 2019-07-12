package com.schemaorg4j.domain.error;

public class SchemaOrg4JError {

    private final String contents;
    private final String message;

    public SchemaOrg4JError(String message, String contents) {
        this.message = message;
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public String getMessage() {
        return message;
    }
}
