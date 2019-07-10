package com.schemaorg4j.codegen.domain;

import java.util.List;
import java.util.Set;

public class SchemaClass {

    private String id;
    private Set<String> subclassOfIds;
    private String label;
    private String comment;

    public SchemaClass(String id, Set<String> subclassOfIds, String label,
        String comment) {
        this.id = id;
        this.subclassOfIds = subclassOfIds;
        this.label = label;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getSubclassOfIds() {
        return subclassOfIds;
    }

    public void setSubclassOfIds(Set<String> subclassOfIds) {
        this.subclassOfIds = subclassOfIds;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
