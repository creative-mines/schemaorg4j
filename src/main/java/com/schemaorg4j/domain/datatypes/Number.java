package com.schemaorg4j.domain.datatypes;

import com.schemaorg4j.util.Lens;
import java.util.Objects;

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

}
