package org.creativemines.schemaorg4j.factory.types;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DATATYPES_PACKAGE;
import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.UTIL_PACKAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.creativemines.schemaorg4j.codegen.domain.SchemaDataType;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.domain.SchemaProperty;
import org.creativemines.schemaorg4j.codegen.factory.types.OrTextTypeHandler;
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
                UTIL_PACKAGE + ".OrText<" + DOMAIN_PACKAGE + ".Book>");
    }

    @Test
    public void handlesOrTypeCorrectlyWithSimpleClass() {
        SchemaProperty schemaProperty = schemaProperty(new HashSet<String>() {{
            add(SchemaDataType.INTEGER.getId());
            add(SchemaDataType.TEXT.getId());
        }});
        SchemaGraph graph = schemaGraph(schemaProperty, "Book");
        assertEquals(new OrTextTypeHandler(graph).handle(schemaProperty).getTypeName().toString(),
                UTIL_PACKAGE + ".OrText<" + DATATYPES_PACKAGE + ".Integer>");
    }
}
