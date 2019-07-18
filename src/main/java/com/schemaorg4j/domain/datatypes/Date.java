package com.schemaorg4j.domain.datatypes;

import java.time.LocalDate;

public class Date {

    public Date() {

    }

    public Date(LocalDate value) {
        this.value = value;
    }

    public LocalDate value;

    public LocalDate getValue() {
        return value;
    }

    public void setValue(LocalDate value) {
        this.value = value;
    }
}
