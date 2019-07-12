package com.schemaorg4j.factory;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaClassBuilder;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import java.util.Arrays;
import java.util.Set;

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
