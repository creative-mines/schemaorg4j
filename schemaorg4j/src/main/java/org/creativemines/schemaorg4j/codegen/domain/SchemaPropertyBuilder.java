package org.creativemines.schemaorg4j.codegen.domain;

import java.util.Set;

public class SchemaPropertyBuilder {

    private String id;
    private Set<String> domainIncludesIds;
    private Set<String> rangeIncludesIds;
    private String comment;
    private String label;

    public SchemaPropertyBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public SchemaPropertyBuilder setDomainIncludesIds(Set<String> domainIncludesIds) {
        this.domainIncludesIds = domainIncludesIds;
        return this;
    }

    public SchemaPropertyBuilder setRangeIncludesIds(Set<String> rangeIncludesIds) {
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