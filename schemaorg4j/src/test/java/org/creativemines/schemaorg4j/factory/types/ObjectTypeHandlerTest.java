package org.creativemines.schemaorg4j.factory.types;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.domain.SchemaProperty;
import org.creativemines.schemaorg4j.codegen.factory.types.ObjectTypeHandler;
import org.junit.Test;

public class ObjectTypeHandlerTest extends TypeFactoryTest {

    @Test
    public void canHandleObjectTypes() {
        SchemaProperty schemaProperty = schemaProperty("http://schema.org/Book");
        SchemaGraph graph = schemaGraph(schemaProperty, "Book");
        assertTrue(new ObjectTypeHandler(graph).canHandle(schemaProperty));
    }

    @Test
    public void handlesObjectTypesCorrectly() {
        SchemaProperty schemaProperty = schemaProperty("http://schema.org/Book");
        SchemaGraph graph = schemaGraph(schemaProperty, "Book");
        assertEquals(new ObjectTypeHandler(graph).handle(schemaProperty).getTypeName().toString(), DOMAIN_PACKAGE + ".Book");
    }
}
