package com.schemaorg4j.codegen.domain;

import java.util.List;

public class SchemaProperty {

    private String id;
    private List<String> domainIncludesIds;
    private List<String> rangeIncludesIds;
    private String comment;
    private String label;

    public SchemaProperty(String id, List<String> domainIncludesIds,
        List<String> rangeIncludesIds, String comment, String label) {
        this.id = id;
        this.domainIncludesIds = domainIncludesIds;
        this.rangeIncludesIds = rangeIncludesIds;
        this.comment = comment;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getDomainIncludesIds() {
        return domainIncludesIds;
    }

    public void setDomainIncludesIds(List<String> domainIncludesIds) {
        this.domainIncludesIds = domainIncludesIds;
    }

    public List<String> getRangeIncludesIds() {
        return rangeIncludesIds;
    }

    public void setRangeIncludesIds(List<String> rangeIncludesIds) {
        this.rangeIncludesIds = rangeIncludesIds;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
