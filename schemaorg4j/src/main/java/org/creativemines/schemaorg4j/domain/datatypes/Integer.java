package org.creativemines.schemaorg4j.domain.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.creativemines.schemaorg4j.util.Lens;

/**
 * <a href = "https://schema.org/Integer">Integer</a> type from Schema.org.  Internally maps to
 * {@link java.lang.Integer}.
 */
public class Integer extends DataType {

    private java.lang.Integer value;
    private Integer nextInteger;

    public Integer() {

    }

    public Integer(java.lang.Integer value) {
        this.value = value;
    }

    public java.lang.Integer getValue() {
        return value;
    }

    public void setValue(java.lang.Integer value) {
        this.value = value;
    }

    public Integer getNextInteger() {
        return nextInteger;
    }

    public void setNextInteger(Integer nextInteger) {
        this.nextInteger = nextInteger;
    }

    public static Lens<Integer, java.lang.Integer> Value = new Lens<>(Integer::getValue, (c, value) -> {
        c.setValue(value);
        return c;
    });

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Integer && Objects
            .equals(((Integer) other).getValue(), value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private List<Integer> nextList;

    public List<Integer> asThingList() {
        if (this.nextList != null) {
            return this.nextList;
        }

        this.nextList = new ArrayList<>();
        this.nextList.add(this);
        Integer tracking = this.getNextInteger();
        while (tracking != null) {
            this.nextList.add(tracking);
            tracking = tracking.getNextInteger();
        }
        return this.nextList;
    }
}
