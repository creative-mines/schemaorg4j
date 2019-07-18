package com.schemaorg4j.domain.datatypes;

import java.time.LocalTime;

public class Time {

    private LocalTime value;

    public Time() {

    }

    public Time(LocalTime value) {
        this.value = value;
    }

    public void setValue(LocalTime value) {
        this.value = value;
    }

    public LocalTime getValue() {
        return value;
    }
}
