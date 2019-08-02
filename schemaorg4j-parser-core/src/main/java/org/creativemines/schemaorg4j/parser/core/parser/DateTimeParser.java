package org.creativemines.schemaorg4j.parser.core.parser;

import java.time.ZonedDateTime;

public interface DateTimeParser {

    ZonedDateTime parse(String date);
}
