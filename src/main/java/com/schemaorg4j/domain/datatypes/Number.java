package com.schemaorg4j.domain.datatypes;

import com.schemaorg4j.util.Lens;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <a href = "https://schema.org/Number">Number</a> type from Schema.org.  Internally maps to
 * {@link java.lang.Float}.
 */
public class Number extends DataType {

    private java.lang.Float value;
    private Number nextNumber;

    public Number() {

    }

    public Number(java.lang.Float value) {
        this.value = value;
    }

    public java.lang.Float getValue() {
        return value;
    }

    public void setValue(java.lang.Float value) {
        this.value = value;
    }

    public Number getNextNumber() {
        return nextNumber;
    }

    public void setNextNumber(Number nextNumber) {
        this.nextNumber = nextNumber;
    }

    public static Lens<Number, java.lang.Float> Value = new Lens<>(Number::getValue, (c, value) -> {
        c.setValue(value);
        return c;
    });

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Number && Objects
            .equals(((Number) other).getValue(), value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private List<Number> nextList;

    public List<Number> asThingList() {
        if (this.nextList != null) {
            return this.nextList;
        }

        this.nextList = new ArrayList<>();
        this.nextList.add(this);
        Number tracking = this.getNextNumber();
        while (tracking != null) {
            this.nextList.add(tracking);
            tracking = tracking.getNextNumber();
        }
        return this.nextList;
    }
}
