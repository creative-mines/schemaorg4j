package org.creativemines.schemaorg4j.factory.types;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DATATYPES_PACKAGE;
import static org.junit.Assert.assertEquals;

import org.creativemines.schemaorg4j.codegen.domain.SchemaDataType;
import org.creativemines.schemaorg4j.codegen.factory.types.SimpleTypeHandler;
import org.junit.Test;

public class SimpleTypeHandlerTest extends TypeFactoryTest {

    @Test
    public void booleanTypeIsHandled() {
        assertEquals(
            new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.BOOLEAN)).getTypeName().toString(),
            DATATYPES_PACKAGE + ".Boolean");
    }

    @Test
    public void dateTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.DATE)).getTypeName().toString(),
                DATATYPES_PACKAGE + ".Date");
    }

    @Test
    public void dateTimeTypeHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.DATETIME)).getTypeName().toString(),
                DATATYPES_PACKAGE + ".DateTime");
    }

    @Test
    public void numberTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.NUMBER)).getTypeName().toString(),
                DATATYPES_PACKAGE + ".Number");
    }

    @Test
    public void floatTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.FLOAT)).getTypeName().toString(),
                DATATYPES_PACKAGE + ".Float");
    }

    @Test
    public void integerTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.INTEGER)).getTypeName().toString(),
                DATATYPES_PACKAGE + ".Integer");
    }

    @Test
    public void textTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.TEXT)).getTypeName().toString(),
                DATATYPES_PACKAGE + ".Text");
    }

    @Test
    public void timeTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.TIME)).getTypeName().toString(),
                DATATYPES_PACKAGE + ".Time");
    }

    @Test
    public void urlTypeIsHandled() {
        assertEquals(new SimpleTypeHandler().handle(schemaProperty(SchemaDataType.URL)).getTypeName().toString(),
                DATATYPES_PACKAGE + ".URL");
    }
}
