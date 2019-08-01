package com.schemaorg4j.domain.datatypes;

import com.schemaorg4j.util.Lens;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <a href = "https://schema.org/DateTime">DateTime</a> type from Schema.org.  Internally maps to
 * {@link java.time.ZonedDateTime}.
 */
public class DateTime extends DataType {

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

    private List<DateTime> nextList;

    public List<DateTime> asThingList() {
        if (this.nextList != null) {
            return this.nextList;
        }

        this.nextList = new ArrayList<>();
        this.nextList.add(this);
        DateTime tracking = this.getNextDateTime();
        while (tracking != null) {
            this.nextList.add(tracking);
            tracking = tracking.getNextDateTime();
        }
        return this.nextList;
    }
}
