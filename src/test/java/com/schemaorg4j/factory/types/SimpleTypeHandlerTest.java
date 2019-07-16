package com.schemaorg4j.factory.types;

import static org.junit.Assert.assertEquals;

import com.schemaorg4j.codegen.domain.SchemaDataType;
import com.schemaorg4j.codegen.factory.types.SimpleTypeHandler;
import org.junit.Test;

public class SimpleTypeHandlerTest extends TypeFactoryTest {

    @Test
    public void booleanTypeIsHandled() {
        assertEquals(
            new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.BOOLEAN)).getTypeName().toString(),
            "java.lang.Boolean");
    }

    @Test
    public void dateTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.DATE)).getTypeName().toString(),
            "java.time.LocalDate");
    }

    @Test
    public void dateTimeTypeHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.DATETIME)).getTypeName().toString(),
            "java.time.ZonedDateTime");
    }

    @Test
    public void numberTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.NUMBER)).getTypeName().toString(),
            "java.lang.Float");
    }

    @Test
    public void floatTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.FLOAT)).getTypeName().toString(),
            "java.lang.Float");
    }

    @Test
    public void integerTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.INTEGER)).getTypeName().toString(),
            "java.lang.Integer");
    }

    @Test
    public void textTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.TEXT)).getTypeName().toString(),
            "java.lang.String");
    }

    @Test
    public void timeTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.TIME)).getTypeName().toString(),
            "java.time.LocalTime");
    }
}
