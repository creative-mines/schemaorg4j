package com.schemaorg4j.parser.core.domain;

import com.schemaorg4j.parser.core.parser.DateParser;
import com.schemaorg4j.parser.core.parser.DateTimeParser;
import com.schemaorg4j.parser.core.parser.TimeParser;
import com.schemaorg4j.parser.core.parser.ZonedDateTimeParser;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Objects;

public class DataValueString implements DataValue {

    private String originalValue;
    private static final ZonedDateTimeParser ZONED_DATE_TIME_PARSER = new ZonedDateTimeParser();

    public DataValueString(String value) {
        this.originalValue = value;
    }

    public Integer asInteger() {
        return new Integer(originalValue);
    }

    public Float asFloat() {
        return new Float(originalValue);
    }

    public ZonedDateTime asDateTime() {
        return ZONED_DATE_TIME_PARSER.parse(originalValue);
    }

    public ZonedDateTime asDateTime(DateTimeParser dateParser) {
        return dateParser.parse(originalValue);
    }

    public LocalDate asDate() {
        return LocalDate.parse(originalValue);
    }

    public LocalDate asDate(DateParser dateParser) {
        return dateParser.parse(originalValue);
    }

    public LocalTime asTime() {
        return LocalTime.parse(originalValue);
    }

    public LocalTime asTime(TimeParser timeParser) {
        return timeParser.parse(originalValue);
    }

    public String asString() {
        return originalValue;
    }

    @Override
    public String toString() {
        return "DataValueAsString<" + originalValue + ">";
    }

    public Boolean asBoolean() {
        return Objects.equals(originalValue, "true") ? Boolean.TRUE
            : (Objects.equals(originalValue, "false") ? Boolean.FALSE : null);
    }
}
