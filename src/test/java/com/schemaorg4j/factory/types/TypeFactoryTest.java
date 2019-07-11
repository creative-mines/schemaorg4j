package com.schemaorg4j.factory.types;

import com.schemaorg4j.codegen.domain.SchemaClassBuilder;
import com.schemaorg4j.codegen.domain.SchemaDataType;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.schemaorg4j.codegen.domain.SchemaPropertyBuilder;
import java.util.HashSet;

class TypeFactoryTest {

    SchemaGraph schemaGraph(SchemaProperty schemaProperty, String classLabel) {
        SchemaGraph schemaGraph = new SchemaGraph();
        schemaGraph.addProperty(schemaProperty);
        schemaGraph.addClass(new SchemaClassBuilder()
            .setId(schemaProperty.getRangeIncludesIds().stream().findFirst().get())
            .setLabel(classLabel).createSchemaClass());
        return schemaGraph;
    }

    SchemaProperty schemaProperty(SchemaDataType type) {
        return schemaProperty(type.getId());
    }

    SchemaProperty schemaProperty(String id) {
        return new SchemaPropertyBuilder().setRangeIncludesIds(new HashSet<String>() {{
            add(id);
        }}).createSchemaProperty();
    }

}
