package com.schemaorg4j.domain.datatypes;

import com.schemaorg4j.util.Lens;
import java.time.ZonedDateTime;
import java.util.Objects;

public class DateTime {

    private ZonedDateTime value;
    private DateTime nextDateTime;

    public DateTime() {

    }

    public DateTime(ZonedDateTime value) {
        this.value = value;
    }

    public ZonedDateTime getValue() {
        return value;
    }

    public void setValue(ZonedDateTime value) {
        this.value = value;
    }

    public DateTime getNextDateTime() {
        return nextDateTime;
    }

    public void setNextDateTime(DateTime nextDateTime) {
        this.nextDateTime = nextDateTime;
    }

    public static Lens<DateTime, ZonedDateTime> Value = new Lens<>(DateTime::getValue, (c, value) -> {
        c.setValue(value);
        return c;
    });

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof DateTime && Objects
            .equals(((DateTime) other).getValue(), value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
