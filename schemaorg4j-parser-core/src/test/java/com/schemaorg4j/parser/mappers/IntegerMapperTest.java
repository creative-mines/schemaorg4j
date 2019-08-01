package com.schemaorg4j.parser.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.schemaorg4j.parser.core.domain.DataValueObject;
import com.schemaorg4j.parser.core.domain.DataValueString;
import com.schemaorg4j.parser.core.mappers.IntegerMapper;
import com.schemaorg4j.parser.core.mappers.MappingArguments;
import org.junit.Test;

public class IntegerMapperTest {

    @Test
    public void canHandleAnInteger() {
        assertTrue(new IntegerMapper().canHandle(Integer.class, new DataValueString("7")));
    }

    @Test
    public void cantHandleAnythingThatsNotAnInteger() {
        assertFalse(new IntegerMapper().canHandle(Float.class, new DataValueString("7")));
    }

    @Test
    public void cantHandleAnythingThatsNotADataValueString() {
        assertFalse(new IntegerMapper().canHandle(Integer.class, new DataValueObject("String")));
    }

    @Test
    public void parsesAsAnInteger() {
        assertEquals(new IntegerMapper().handle(new MappingArguments(new DataValueString("7"))), 7);
    }
}
