package org.creativemines.schemaorg4j.codegen.domain;

import java.util.List;
import java.util.Set;

public class SchemaClassBuilder {

    private String id;
    private Set<String> subclassOfIds;
    private String label;
    private String comment;

    public SchemaClassBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public SchemaClassBuilder setSubclassOfIds(Set<String> subclassOfIds) {
        this.subclassOfIds = subclassOfIds;
        return this;
    }

    public SchemaClassBuilder setLabel(String label) {
        this.label = label;
        return this;
    }

    public SchemaClassBuilder setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public SchemaClass createSchemaClass() {
        return new SchemaClass(id, subclassOfIds, label, comment);
    }
}