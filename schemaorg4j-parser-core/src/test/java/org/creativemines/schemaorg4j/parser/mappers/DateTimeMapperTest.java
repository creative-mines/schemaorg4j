package org.creativemines.schemaorg4j.parser.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.time.ZonedDateTime;

import org.creativemines.schemaorg4j.parser.core.domain.DataValueObject;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueString;
import org.creativemines.schemaorg4j.parser.core.mappers.DateTimeMapper;
import org.creativemines.schemaorg4j.parser.core.mappers.MappingArguments;
import org.junit.Test;

public class DateTimeMapperTest {

    @Test
    public void canHandleAnDateTime() {
        assertTrue(new DateTimeMapper().canHandle(ZonedDateTime.class, new DataValueString("7.0")));
    }

    @Test
    public void cantHandleAnythingThatsNotAnDateTime() {
        assertFalse(new DateTimeMapper().canHandle(Integer.class, new DataValueString("7.0")));
    }

    @Test
    public void cantHandleAnythingThatsNotADataValueDateTime() {
        assertFalse(
            new DateTimeMapper()
                .canHandle(ZonedDateTime.class, new DataValueObject("DateTime")));
    }

    @Test
    public void parsesAsAnDateTime() {
        ZonedDateTime dateTime = (ZonedDateTime) new DateTimeMapper()
            .handle(new MappingArguments(new DataValueString("2016-04-21T20:00")));
        assertEquals(dateTime.toInstant(), Instant.ofEpochMilli(1461268800000L));
    }

    @Test
    public void parsesAsAnDateTimeWithSeconds() {
        ZonedDateTime dateTime = (ZonedDateTime) new DateTimeMapper()
            .handle(new MappingArguments(new DataValueString("2016-04-21T20:00:00")));
        assertEquals(dateTime.toInstant(), Instant.ofEpochMilli(1461268800000L));
    }
}
