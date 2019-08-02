package org.creativemines.schemaorg4j.codegen.domain;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SchemaProperty {

    private String id;
    private Set<String> domainIncludesIds;
    private Set<String> rangeIncludesIds;
    private String comment;
    private String label;

    public SchemaProperty(String id, Set<String> domainIncludesIds,
        Set<String> rangeIncludesIds, String comment, String label) {
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

    public Set<String> getDomainIncludesIds() {
        return domainIncludesIds != null ? domainIncludesIds : Collections.emptySet();
    }

    public void setDomainIncludesIds(Set<String> domainIncludesIds) {
        this.domainIncludesIds = domainIncludesIds;
    }

    public Set<String> getRangeIncludesIds() {
        return rangeIncludesIds != null ? rangeIncludesIds : Collections.emptySet();
    }

    public void setRangeIncludesIds(Set<String> rangeIncludesIds) {
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
