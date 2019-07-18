package com.schemaorg4j.domain.datatypes;

import com.schemaorg4j.util.Lens;
import java.util.Objects;

public class Boolean {

    private java.lang.Boolean value;
    private Boolean nextBoolean;

    public Boolean() {

    }

    public Boolean(java.lang.Boolean value) {
        this.value = value;
    }

    public void setValue(java.lang.Boolean value) {
        this.value = value;
    }

    public java.lang.Boolean getValue() {
        return this.value;
    }

    public Boolean getNextBoolean() {
        return nextBoolean;
    }

    public void setNextBoolean(Boolean nextBoolean) {
        this.nextBoolean = nextBoolean;
    }

    public static Lens<Boolean, java.lang.Boolean> Value = new Lens<>(Boolean::getValue, (c, value) -> {
        c.setValue(value);
        return c;
    });

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Boolean && Objects
            .equals(((Boolean) other).getValue(), value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
