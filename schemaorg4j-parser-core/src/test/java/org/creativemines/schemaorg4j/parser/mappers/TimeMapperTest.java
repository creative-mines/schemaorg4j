package org.creativemines.schemaorg4j.parser.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;

import org.creativemines.schemaorg4j.parser.core.domain.DataValueObject;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueString;
import org.creativemines.schemaorg4j.parser.core.mappers.MappingArguments;
import org.creativemines.schemaorg4j.parser.core.mappers.TimeMapper;
import org.junit.Ignore;
import org.junit.Test;

public class TimeMapperTest {

    @Test
    public void canHandleAnTime() {
        assertTrue(new TimeMapper().canHandle(LocalTime.class, new DataValueString("7")));
    }

    @Test
    public void cantHandleAnythingThatsNotAnTime() {
        assertFalse(new TimeMapper().canHandle(Float.class, new DataValueString("7")));
    }

    @Test
    public void cantHandleAnythingThatsNotADataValueString() {
        assertFalse(new TimeMapper().canHandle(LocalTime.class, new DataValueObject("String")));
    }

    @Test
    public void parsesAsAnTime() {
        LocalTime time = (LocalTime) new TimeMapper().handle(new MappingArguments(new DataValueString("06:30")));
        assertEquals(time.getHour(), 6);
        assertEquals(time.getMinute(), 30);
    }

    @Test
    @Ignore("Not yet capable of parinsg zoned times")
    public void parsesAsAnZonedTime() {
        LocalTime time = (LocalTime) new TimeMapper().handle(new MappingArguments(new DataValueString("06:30Z")));
        assertEquals(time.getHour(), 6);
        assertEquals(time.getMinute(), 30);
    }
}
