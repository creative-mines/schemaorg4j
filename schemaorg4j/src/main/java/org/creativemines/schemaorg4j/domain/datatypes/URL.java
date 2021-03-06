package org.creativemines.schemaorg4j.domain.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.creativemines.schemaorg4j.util.Lens;

/**
 * <a href = "https://schema.org/URL">URL</a> type from Schema.org.  Internally maps to
 * {@link java.lang.String}.
 */
public class URL extends DataType {

    private String value;
    private URL nextUrl;

    public URL() {

    }

    public URL(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public URL getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(URL nextUrl) {
        this.nextUrl = nextUrl;
    }

    public static Lens<URL, String> Value = new Lens<>(URL::getValue, (c, value) -> {
        c.setValue(value);
        return c;
    });

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof URL && Objects
            .equals(((URL) other).getValue(), value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private List<URL> nextList;

    public List<URL> asThingList() {
        if (this.nextList != null) {
            return this.nextList;
        }

        this.nextList = new ArrayList<>();
        this.nextList.add(this);
        URL tracking = this.getNextUrl();
        while (tracking != null) {
            this.nextList.add(tracking);
            tracking = tracking.getNextUrl();
        }
        return this.nextList;
    }
}
