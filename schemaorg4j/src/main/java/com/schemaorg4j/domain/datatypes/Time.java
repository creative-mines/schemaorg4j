package com.schemaorg4j.domain.datatypes;

import com.schemaorg4j.util.Lens;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <a href = "https://schema.org/Time">Time</a> type from Schema.org.  Internally maps to
 * {@link java.time.LocalTime}.
 */
public class Time extends DataType {

    private LocalTime value;
    private Time nextTime;

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

    public Time getNextTime() {
        return nextTime;
    }

    public void setNextTime(Time nextTime) {
        this.nextTime = nextTime;
    }

    public static Lens<Time, java.time.LocalTime> Value = new Lens<>(Time::getValue, (c, value) -> {
        c.setValue(value);
        return c;
    });

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Time && Objects
            .equals(((Time) other).getValue(), value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private List<Time> nextList;

    public List<Time> asThingList() {
        if (this.nextList != null) {
            return this.nextList;
        }

        this.nextList = new ArrayList<>();
        this.nextList.add(this);
        Time tracking = this.getNextTime();
        while (tracking != null) {
            this.nextList.add(tracking);
            tracking = tracking.getNextTime();
        }
        return this.nextList;
    }
}
