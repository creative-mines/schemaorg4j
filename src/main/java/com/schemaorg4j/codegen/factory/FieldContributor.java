package com.schemaorg4j.codegen.factory;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.factory.types.TypeFactory;
import com.squareup.javapoet.FieldSpec;
import javax.lang.model.element.Modifier;

public class FieldContributor implements BlueprintContributor {

    private final SchemaGraph graph;
    private final TypeFactory typeFactory;

    public FieldContributor(SchemaGraph graph, TypeFactory typeFactory) {
        this.graph = graph;
        this.typeFactory = typeFactory;
    }

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        graph.getProperties(schemaClass.getId()).forEach(property -> {
            FieldSpec spec = FieldSpec
                .builder(typeFactory.build(property), property.getLabel(), Modifier.PRIVATE)
                .build();
            blueprint.addField(spec);
        });
    }
}
