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
            "com.schemaorg4j.domain.datatypes.Boolean");
    }

    @Test
    public void dateTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.DATE)).getTypeName().toString(),
            "com.schemaorg4j.domain.datatypes.Date");
    }

    @Test
    public void dateTimeTypeHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.DATETIME)).getTypeName().toString(),
            "com.schemaorg4j.domain.datatypes.DateTime");
    }

    @Test
    public void numberTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.NUMBER)).getTypeName().toString(),
            "com.schemaorg4j.domain.datatypes.Number");
    }

    @Test
    public void floatTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.FLOAT)).getTypeName().toString(),
            "com.schemaorg4j.domain.datatypes.Float");
    }

    @Test
    public void integerTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.INTEGER)).getTypeName().toString(),
            "com.schemaorg4j.domain.datatypes.Integer");
    }

    @Test
    public void textTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.TEXT)).getTypeName().toString(),
            "com.schemaorg4j.domain.datatypes.Text");
    }

    @Test
    public void timeTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.TIME)).getTypeName().toString(),
            "com.schemaorg4j.domain.datatypes.Time");
    }

    @Test
    public void urlTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.URL)).getTypeName().toString(),
            "com.schemaorg4j.domain.datatypes.URL");
    }
}
