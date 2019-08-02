package org.creativemines.schemaorg4j.parser.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.creativemines.schemaorg4j.parser.core.domain.DataValueObject;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueString;
import org.creativemines.schemaorg4j.parser.core.mappers.MappingArguments;
import org.creativemines.schemaorg4j.parser.core.mappers.StringMapper;
import org.junit.Test;

public class StringMapperTest {
    @Test
    public void canHandleAnString() {
        assertTrue(new StringMapper().canHandle(String.class, new DataValueString("7.0")));
    }

    @Test
    public void cantHandleAnythingThatsNotAnString() {
        assertFalse(new StringMapper().canHandle(Integer.class, new DataValueString("7.0")));
    }

    @Test
    public void cantHandleAnythingThatsNotADataValueString() {
        assertFalse(new StringMapper().canHandle(String.class, new DataValueObject("String")));
    }

    @Test
    public void parsesAsAnString() {
        assertEquals(new StringMapper().handle(new MappingArguments(new DataValueString("7.0"))), "7.0");
    }
}
