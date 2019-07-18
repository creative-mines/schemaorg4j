package com.schemaorg4j.domain.datatypes;

import com.schemaorg4j.util.Lens;
import java.time.LocalTime;
import java.util.Objects;

public class Time {

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

}
