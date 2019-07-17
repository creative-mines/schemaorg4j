package com.schemaorg4j.factory.types;

import static org.junit.Assert.assertEquals;

import com.schemaorg4j.codegen.domain.SchemaDataType;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.factory.types.SimpleTypeFactory;
import org.junit.Test;

public class SimpleTypeFactoryTest extends TypeFactoryTest {

    @Test
    public void integerTypeCanBeGenerated() {
        assertEquals(
            new SimpleTypeFactory(new SchemaGraph()).build(schemaProperty(SchemaDataType.INTEGER)).getTypeName().toString(),
            "com.schemaorg4j.domain.datatypes.Integer");
    }
}
