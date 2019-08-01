package com.schemaorg4j.parser.core.mappers;

import com.schemaorg4j.domain.datatypes.Date;
import com.schemaorg4j.domain.datatypes.DateTime;
import com.schemaorg4j.domain.datatypes.Text;
import com.schemaorg4j.domain.datatypes.Time;
import com.schemaorg4j.domain.datatypes.URL;
import com.schemaorg4j.parser.core.domain.DataValue;
import com.schemaorg4j.parser.core.domain.DataValueString;
import java.util.Objects;

public class BasicDataTypeMapper {
    public Object asValue(Class<?> dataValueClass, DataValue dataValue) {
        if (Objects.equals(dataValueClass, Date.class)) {
            return ((DataValueString) dataValue).asDate();
        } else if (Objects.equals(dataValueClass, Time.class)) {
            return ((DataValueString) dataValue).asTime();
        } else if (Objects.equals(dataValueClass, DateTime.class)) {
            return ((DataValueString) dataValue).asDateTime();
        } else if (Objects.equals(dataValueClass, com.schemaorg4j.domain.datatypes.Integer.class)) {
            return ((DataValueString) dataValue).asInteger();
        } else if (Objects.equals(dataValueClass, com.schemaorg4j.domain.datatypes.Float.class)) {
            return ((DataValueString) dataValue).asFloat();
        } else if (Objects.equals(dataValueClass, Text.class)) {
            return ((DataValueString) dataValue).asString();
        } else if (Objects.equals(dataValueClass, URL.class)) {
            return ((DataValueString) dataValue).asString();
        } else if (Objects.equals(dataValueClass, com.schemaorg4j.domain.datatypes.Number.class)) {
            return ((DataValueString) dataValue).asFloat();
        } else if (Objects.equals(dataValueClass, com.schemaorg4j.domain.datatypes.Boolean.class)) {
            return ((DataValueString) dataValue).asBoolean();
        } else {
            throw new UnsupportedOperationException(
                "No primitive class could support " + dataValueClass.toString());
        }
    }
}
