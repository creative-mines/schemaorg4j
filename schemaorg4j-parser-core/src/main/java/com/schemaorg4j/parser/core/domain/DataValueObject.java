package com.schemaorg4j.parser.core.domain;

import java.util.HashMap;
import java.util.Map;

public class DataValueObject implements DataValue {

    private String originalValue;
    private String type;
    private Map<Field, DataValue> fields;

    public DataValueObject(String type) {
        this.fields = new HashMap<>();
        if (type != null) {
            this.type = type.replace("http://schema.org/", "")
                .replace("https://schema.org/", "");
        }
    }

    public String getType() {
        return type;
    }

    public void putField(String fieldName, DataValue dataValueString) {
        fields.put(new Field(fieldName), dataValueString);
    }

    public Iterable<Field> getFields() {
        return fields.keySet();
    }

    public DataValue getValueFor(Field f) {
        return fields.get(f);
    }

    public DataValue getValueFor(String fieldName) {
        return fields.get(new Field(fieldName));
    }

    public DataValueObject asType(String type) {
        this.type = type;
        return this;
    }

}
