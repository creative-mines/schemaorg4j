package org.creativemines.schemaorg4j.parser.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.creativemines.schemaorg4j.parser.core.domain.DataValueObject;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueString;
import org.creativemines.schemaorg4j.parser.core.mappers.FloatMapper;
import org.creativemines.schemaorg4j.parser.core.mappers.MappingArguments;
import org.junit.Test;

public class FloatMapperTest {

    @Test
    public void canHandleAnFloat() {
        assertTrue(new FloatMapper().canHandle(Float.class, new DataValueString("7.0")));
    }

    @Test
    public void cantHandleAnythingThatsNotAnFloat() {
        assertFalse(new FloatMapper().canHandle(Integer.class, new DataValueString("7.0")));
    }

    @Test
    public void cantHandleAnythingThatsNotADataValueString() {
        assertFalse(new FloatMapper().canHandle(Float.class, new DataValueObject("String")));
    }

    @Test
    public void parsesAsAnFloat() {
        assertEquals(new FloatMapper().handle(new MappingArguments(new DataValueString("7.0"))), 7.0f);
    }
}
