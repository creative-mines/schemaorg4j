package org.creativemines.schemaorg4j.parser.core.parser;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeParser implements DateTimeParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter
        .ofPattern("yyyy-MM-dd'T'HH:mm[:ss][XXX]").withZone(ZoneId.of("UTC"));

    @Override
    public ZonedDateTime parse(String date) {
        return ZonedDateTime.parse(date, FORMATTER);
    }
}
