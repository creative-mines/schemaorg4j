package org.creativemines.schemaorg4j.parser.mappers;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.creativemines.schemaorg4j.parser.core.domain.DataValueString;
import org.creativemines.schemaorg4j.parser.core.mappers.MappingArguments;
import org.creativemines.schemaorg4j.parser.core.mappers.NullMapper;
import org.junit.Test;

public class NullMapperTest {

    @Test
    public void nullMapperCanHandleAnything() {
        assertTrue(new NullMapper().canHandle(Integer.class, new DataValueString("test")));
        assertTrue(new NullMapper().canHandle(String.class, new DataValueString("test")));
    }

    @Test
    public void nullMapperAlwaysReturnsNull() {
        assertNull(new NullMapper().handle(new MappingArguments(new DataValueString("test"))));
    }
}
