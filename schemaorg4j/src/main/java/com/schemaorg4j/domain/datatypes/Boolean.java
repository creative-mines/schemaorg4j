package com.schemaorg4j.domain.datatypes;

import com.schemaorg4j.util.Lens;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <a href = "https://schema.org/Integer">Integer</a> type from Schema.org.  Internally maps to
 * {@link java.lang.Integer}.
 */
public class Boolean extends DataType {

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

    private List<Boolean> nextList;

    public List<Boolean> asThingList() {
        if (this.nextList != null) {
            return this.nextList;
        }

        this.nextList = new ArrayList<>();
        this.nextList.add(this);
        Boolean tracking = this.getNextBoolean();
        while (tracking != null) {
            this.nextList.add(tracking);
            tracking = tracking.getNextBoolean();
        }
        return this.nextList;
    }
}
