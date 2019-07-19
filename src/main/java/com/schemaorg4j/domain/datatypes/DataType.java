package com.schemaorg4j.domain.datatypes;

import com.schemaorg4j.domain.error.SchemaOrg4JError;
import java.util.List;

/**
 * Superclass for all Schema.org <a href = "https://schema.org/DataType">DataTypes</a>.
 */
public abstract class DataType {

    private List<SchemaOrg4JError> errors;

    public List<SchemaOrg4JError> getSchemaOrg4JErrors() {
        return errors;
    }

    public void setSchemaOrg4JErrors(List<SchemaOrg4JError> errors) {
        this.errors = errors;
    }
}
