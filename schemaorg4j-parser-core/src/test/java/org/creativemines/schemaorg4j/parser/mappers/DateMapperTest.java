package org.creativemines.schemaorg4j.parser.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.LocalDate;
import java.util.Date;

import org.creativemines.schemaorg4j.parser.core.domain.DataValueObject;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueString;
import org.creativemines.schemaorg4j.parser.core.mappers.DateMapper;
import org.creativemines.schemaorg4j.parser.core.mappers.MappingArguments;
import org.junit.Assert;
import org.junit.Test;

public class DateMapperTest {
    @Test
    public void canHandleAnDate() {
        Assert.assertTrue(new DateMapper().canHandle(LocalDate.class, new DataValueString("7.0")));
    }

    @Test
    public void cantHandleAnythingThatsNotAnDate() {
        assertFalse(new DateMapper().canHandle(Integer.class, new DataValueString("7.0")));
    }

    @Test
    public void cantHandleAnythingThatsNotADataValueDate() {
        assertFalse(new DateMapper().canHandle(Date.class, new DataValueObject("Date")));
    }

    @Test
    public void parsesAsAnDate() {
        LocalDate date = (LocalDate) new DateMapper().handle(new MappingArguments(new DataValueString("2012-06-02")));
        assertEquals(date.getMonth().getValue(), 6);
        assertEquals(date.getYear(), 2012);
    }
}
