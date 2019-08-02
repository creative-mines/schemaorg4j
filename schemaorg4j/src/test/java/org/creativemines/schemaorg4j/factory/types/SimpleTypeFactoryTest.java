package org.creativemines.schemaorg4j.factory.types;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DATATYPES_PACKAGE;
import static org.junit.Assert.assertEquals;

import org.creativemines.schemaorg4j.codegen.domain.SchemaDataType;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.factory.types.SimpleTypeFactory;
import org.junit.Test;

public class SimpleTypeFactoryTest extends TypeFactoryTest {

    @Test
    public void integerTypeCanBeGenerated() {
        assertEquals(
            new SimpleTypeFactory(new SchemaGraph()).build(schemaProperty(SchemaDataType.INTEGER)).getTypeName().toString(),
            DATATYPES_PACKAGE + ".Integer");
    }
}
