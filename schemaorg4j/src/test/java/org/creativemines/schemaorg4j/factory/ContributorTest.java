package org.creativemines.schemaorg4j.factory;

import java.util.Arrays;
import java.util.Set;

import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;
import org.creativemines.schemaorg4j.codegen.domain.SchemaClassBuilder;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;

public class ContributorTest {

    protected SchemaGraph schemaGraph(SchemaClass... schemaClass) {
        SchemaGraph graph = new SchemaGraph();
        Arrays.stream(schemaClass).forEach(graph::addClass);
        return graph;
    }

    protected SchemaClass schemaClass(String id, String label, Set<String> subclassOfIds) {
        return new SchemaClassBuilder().setId(id)
            .setLabel(label).setSubclassOfIds(
                subclassOfIds).createSchemaClass();
    }

}
