package com.schemaorg4j.codegen.domain;

import java.util.List;

public class SchemaPropertyBuilder {

    private String id;
    private List<String> domainIncludesIds;
    private List<String> rangeIncludesIds;
    private String comment;
    private String label;

    public SchemaPropertyBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public SchemaPropertyBuilder setDomainIncludesIds(List<String> domainIncludesIds) {
        this.domainIncludesIds = domainIncludesIds;
        return this;
    }

    public SchemaPropertyBuilder setRangeIncludesIds(List<String> rangeIncludesIds) {
        this.rangeIncludesIds = rangeIncludesIds;
        return this;
    }

    public SchemaPropertyBuilder setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public SchemaPropertyBuilder setLabel(String label) {
        this.label = label;
        return this;
    }

    public SchemaProperty createSchemaProperty() {
        return new SchemaProperty(id, domainIncludesIds, rangeIncludesIds, comment, label);
    }
}