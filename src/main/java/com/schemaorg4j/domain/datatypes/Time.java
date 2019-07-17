package com.schemaorg4j.domain.datatypes;

import java.time.LocalTime;

public class Time {

    public Time(LocalTime value) {
        this.value = value;
    }

    private LocalTime value;

    public void setValue(LocalTime value) {
        this.value = value;
    }

    public LocalTime getValue() {
        return value;
    }
}
