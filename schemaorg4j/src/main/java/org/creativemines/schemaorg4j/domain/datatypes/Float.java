package org.creativemines.schemaorg4j.domain.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.creativemines.schemaorg4j.util.Lens;

/**
 * <a href = "https://schema.org/Float">Float</a> type from Schema.org.  Internally maps to
 * {@link java.lang.Float}.
 */
public class Float extends DataType {

    private java.lang.Float value;
    private Float nextFloat;

    public Float() {

    }

    public Float(java.lang.Float value) {
        this.value = value;
    }

    public java.lang.Float getValue() {
        return value;
    }

    public void setValue(java.lang.Float value) {
        this.value = value;
    }

    public Float getNextFloat() {
        return nextFloat;
    }

    public void setNextFloat(Float nextFloat) {
        this.nextFloat = nextFloat;
    }

    public static Lens<Float, java.lang.Float> Value = new Lens<>(Float::getValue, (c, value) -> {
        c.setValue(value);
        return c;
    });

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Float && Objects
            .equals(((Float) other).getValue(), value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private List<Float> nextList;

    public List<Float> asThingList() {
        if (this.nextList != null) {
            return this.nextList;
        }

        this.nextList = new ArrayList<>();
        this.nextList.add(this);
        Float tracking = this.getNextFloat();
        while (tracking != null) {
            this.nextList.add(tracking);
            tracking = tracking.getNextFloat();
        }
        return this.nextList;
    }
}
