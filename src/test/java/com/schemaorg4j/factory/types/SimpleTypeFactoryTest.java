package com.schemaorg4j.factory.types;

import static org.junit.Assert.assertEquals;

import com.schemaorg4j.codegen.domain.SchemaDataType;
import com.schemaorg4j.codegen.factory.types.SimpleTypeFactory;
import org.junit.Test;

public class SimpleTypeFactoryTest extends TypeFactoryTest {

    @Test
    public void integerTypeCanBeGenerated() {
        assertEquals(
            new SimpleTypeFactory().build(schemaProperty(SchemaDataType.INTEGER)).toString(),
            "java.lang.Integer");
    }
}
