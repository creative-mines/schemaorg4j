package org.creativemines.schemaorg4j.codegen.domain;

public class SchemaEnumMember {

    private String id;
    private String enumId;
    private String label;

    public SchemaEnumMember(String id, String enumId, String label) {
        this.id = id;
        this.enumId = enumId;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnumId() {
        return enumId;
    }

    public void setEnumId(String enumId) {
        this.enumId = enumId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
