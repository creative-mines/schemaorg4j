package com.schemaorg4j.domain.error;

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
