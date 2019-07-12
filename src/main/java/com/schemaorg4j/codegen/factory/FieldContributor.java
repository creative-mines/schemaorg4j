package com.schemaorg4j.codegen.factory;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.factory.types.TypeFactory;
import com.schemaorg4j.codegen.jsonld.Util;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import javax.lang.model.element.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldContributor implements BlueprintContributor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldContributor.class);
    private final SchemaGraph graph;
    private final TypeFactory typeFactory;

    public FieldContributor(SchemaGraph graph, TypeFactory typeFactory) {
        this.graph = graph;
        this.typeFactory = typeFactory;
    }

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {

        graph.getProperties(schemaClass.getId()).forEach(property -> {
            TypeName type = typeFactory.build(property);

            try {

                if (type != null) {
                    FieldSpec spec = FieldSpec
                        .builder(type, Util.orLabelFromId(property.getLabel(), property.getId()),
                            Modifier.PRIVATE)
                        .build();
                    blueprint.addField(spec);
                } else {
                    LOGGER
                        .warn("Could not determine type for field {} on {}, skipping",
                            property.getId(),
                            schemaClass.getId());
                }
            } catch (IllegalArgumentException e) {
                LOGGER.warn("Could not create field '{}' (from {})", property.getLabel(),
                    property.getId());
                LOGGER.debug("Original error ", e);
            }
        });
    }
}
