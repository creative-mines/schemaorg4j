package org.creativemines.schemaorg4j.factory.types;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.creativemines.schemaorg4j.SchemaOrg4JConstants;
import org.creativemines.schemaorg4j.codegen.domain.SchemaClassBuilder;
import org.creativemines.schemaorg4j.codegen.domain.SchemaDataType;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.domain.SchemaProperty;
import org.creativemines.schemaorg4j.codegen.factory.types.MultiTypeHandler;
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

        assertEquals(new MultiTypeHandler(graph).handle(schemaProperty).getTypeName().toString(),
                SchemaOrg4JConstants.COMBO_TYPE_PACKAGE + ".BookOrIntegerOrText");
    }
}
