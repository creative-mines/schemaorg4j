package com.schemaorg4j.domain.datatypes;

import java.time.ZonedDateTime;

public class DateTime {

    public DateTime(ZonedDateTime value) {
        this.value = value;
    }

    private ZonedDateTime value;

    public ZonedDateTime getValue() {
        return value;
    }

    public void setValue(ZonedDateTime value) {
        this.value = value;
    }
}
