package com.schemaorg4j.factory.types;

import static org.junit.Assert.assertEquals;

import com.schemaorg4j.codegen.domain.SchemaClassBuilder;
import com.schemaorg4j.codegen.domain.SchemaDataType;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.schemaorg4j.codegen.factory.types.MultiTypeHandler;
import java.util.HashSet;
import org.junit.Test;

public class MultiTypeHandlerTest extends TypeFactoryTest {

    @Test
    public void alphabeticallySortsTypeNamesForGeneration() {
        SchemaProperty schemaProperty = schemaProperty(new HashSet<String>() {{
            add(SchemaDataType.INTEGER.getId());
            add(SchemaDataType.TEXT.getId());
            add("http://schema.org/Book");
        }});

        SchemaGraph graph = new SchemaGraph();
        graph.addClass(new SchemaClassBuilder().setId("http://schema.org/Book").setLabel("Book")
            .createSchemaClass());

        assertEquals(new MultiTypeHandler(graph).handle(schemaProperty).toString(),
            "com.schemaorg4j.domain.combo.BookOrIntegerOrText");
    }
}
