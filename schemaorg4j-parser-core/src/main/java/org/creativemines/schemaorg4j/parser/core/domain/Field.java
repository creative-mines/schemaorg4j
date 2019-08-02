package org.creativemines.schemaorg4j.parser.core.domain;

import java.util.Objects;

public class Field {

    private String name;

    public Field(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Field)) {
            return false;
        }

        Field otherField = (Field) other;
        return Objects.equals(name, otherField.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }
}
