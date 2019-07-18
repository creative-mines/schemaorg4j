package com.schemaorg4j.domain.datatypes;

import com.schemaorg4j.util.Lens;
import java.util.Objects;

public class URL {

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

}
