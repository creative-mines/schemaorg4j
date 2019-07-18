package com.schemaorg4j.domain.datatypes;

public class URL {

    public URL() {

    }

    public URL(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
