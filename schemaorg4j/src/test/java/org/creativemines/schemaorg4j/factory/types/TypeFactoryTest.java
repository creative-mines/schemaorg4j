package org.creativemines.schemaorg4j.factory.types;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.creativemines.schemaorg4j.codegen.domain.SchemaClassBuilder;
import org.creativemines.schemaorg4j.codegen.domain.SchemaDataType;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.domain.SchemaProperty;
import org.creativemines.schemaorg4j.codegen.domain.SchemaPropertyBuilder;

class TypeFactoryTest {

    SchemaGraph schemaGraph(SchemaProperty schemaProperty, String classLabel) {
        SchemaGraph schemaGraph = new SchemaGraph();
        schemaGraph.addProperty(schemaProperty);
        schemaProperty.getRangeIncludesIds().stream().forEach(includes -> {
            schemaGraph.addClass(new SchemaClassBuilder()
                .setId(includes)
                .setLabel(classLabel).createSchemaClass());
        });
        return schemaGraph;
    }

    SchemaProperty schemaProperty(SchemaDataType type) {
        return schemaProperty(type.getId());
    }

    SchemaProperty schemaProperty(String id) {
        return schemaProperty(Collections.singleton(id));
    }

    SchemaProperty schemaProperty(Collection<String> ids) {
        return new SchemaPropertyBuilder().setRangeIncludesIds(new HashSet<String>() {{
            this.addAll(ids);
        }}).createSchemaProperty();
    }

}
