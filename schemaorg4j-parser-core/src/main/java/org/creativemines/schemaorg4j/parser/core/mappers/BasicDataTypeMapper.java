package org.creativemines.schemaorg4j.parser.core.mappers;

import java.util.Objects;

import org.creativemines.schemaorg4j.domain.datatypes.Date;
import org.creativemines.schemaorg4j.domain.datatypes.DateTime;
import org.creativemines.schemaorg4j.domain.datatypes.Text;
import org.creativemines.schemaorg4j.domain.datatypes.Time;
import org.creativemines.schemaorg4j.domain.datatypes.URL;
import org.creativemines.schemaorg4j.parser.core.domain.DataValue;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueString;

public class BasicDataTypeMapper {
    public Object asValue(Class<?> dataValueClass, DataValue dataValue) {
        if (Objects.equals(dataValueClass, Date.class)) {
            return ((DataValueString) dataValue).asDate();
        } else if (Objects.equals(dataValueClass, Time.class)) {
            return ((DataValueString) dataValue).asTime();
        } else if (Objects.equals(dataValueClass, DateTime.class)) {
            return ((DataValueString) dataValue).asDateTime();
        } else if (Objects.equals(dataValueClass, org.creativemines.schemaorg4j.domain.datatypes.Integer.class)) {
            return ((DataValueString) dataValue).asInteger();
        } else if (Objects.equals(dataValueClass, org.creativemines.schemaorg4j.domain.datatypes.Float.class)) {
            return ((DataValueString) dataValue).asFloat();
        } else if (Objects.equals(dataValueClass, Text.class)) {
            return ((DataValueString) dataValue).asString();
        } else if (Objects.equals(dataValueClass, URL.class)) {
            return ((DataValueString) dataValue).asString();
        } else if (Objects.equals(dataValueClass, org.creativemines.schemaorg4j.domain.datatypes.Number.class)) {
            return ((DataValueString) dataValue).asFloat();
        } else if (Objects.equals(dataValueClass, org.creativemines.schemaorg4j.domain.datatypes.Boolean.class)) {
            return ((DataValueString) dataValue).asBoolean();
        } else {
            throw new UnsupportedOperationException(
                "No primitive class could support " + dataValueClass.toString());
        }
    }
}
