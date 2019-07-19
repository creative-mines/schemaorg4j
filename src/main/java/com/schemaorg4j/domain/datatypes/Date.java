package com.schemaorg4j.domain.datatypes;

import com.schemaorg4j.util.Lens;
import java.time.LocalDate;
import java.util.Objects;

/**
 * <a href = "https://schema.org/Date">Date</a> type from Schema.org.  Internally maps to
 * {@link java.time.LocalDate}.
 */
public class Date extends DataType {

    public LocalDate value;
    private Date nextDate;

    public Date() {

    }

    public Date(LocalDate value) {
        this.value = value;
    }

    public LocalDate getValue() {
        return value;
    }

    public void setValue(LocalDate value) {
        this.value = value;
    }

    public Date getNextDate() {
        return nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

    public static Lens<Date, LocalDate> Value = new Lens<>(Date::getValue, (c, value) -> {
        c.setValue(value);
        return c;
    });

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Date && Objects
            .equals(((Date) other).getValue(), value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
