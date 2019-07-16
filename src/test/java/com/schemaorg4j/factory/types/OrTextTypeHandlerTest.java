package com.schemaorg4j.factory.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.schemaorg4j.codegen.domain.SchemaDataType;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.schemaorg4j.codegen.factory.types.OrTextTypeHandler;
import java.util.HashSet;
import org.junit.Test;

public class OrTextTypeHandlerTest extends TypeFactoryTest {

    @Test
    public void canHandleOrType() {
        SchemaProperty schemaProperty = schemaProperty(new HashSet<String>() {{
            add("http://schema.org/Book");
            add(SchemaDataType.TEXT.getId());
        }});
        SchemaGraph graph = schemaGraph(schemaProperty, "Book");
        assertTrue(new OrTextTypeHandler(graph).canHandle(schemaProperty));
    }

    @Test
    public void handlesOrTypeCorrectly() {
        SchemaProperty schemaProperty = schemaProperty(new HashSet<String>() {{
            add("http://schema.org/Book");
            add(SchemaDataType.TEXT.getId());
        }});
        SchemaGraph graph = schemaGraph(schemaProperty, "Book");
        assertEquals(new OrTextTypeHandler(graph).handle(schemaProperty).getTypeName().toString(),
            "com.schemaorg4j.util.OrText<com.schemaorg4j.domain.Book>");
    }

    @Test
    public void handlesOrTypeCorrectlyWithSimpleClass() {
        SchemaProperty schemaProperty = schemaProperty(new HashSet<String>() {{
            add(SchemaDataType.INTEGER.getId());
            add(SchemaDataType.TEXT.getId());
        }});
        SchemaGraph graph = schemaGraph(schemaProperty, "Book");
        assertEquals(new OrTextTypeHandler(graph).handle(schemaProperty).getTypeName().toString(),
            "com.schemaorg4j.util.OrText<java.lang.Integer>");
    }

    @Test
    public void handlesOrUrlAsSimpleStringDataType() {
        SchemaProperty schemaProperty = schemaProperty(new HashSet<String>() {{
            add(SchemaDataType.URL.getId());
            add(SchemaDataType.TEXT.getId());
        }});

        SchemaGraph graph = schemaGraph(schemaProperty, "Book");
        assertEquals(new OrTextTypeHandler(graph).handle(schemaProperty).getTypeName().toString(),
            "java.lang.String");
    }
}
