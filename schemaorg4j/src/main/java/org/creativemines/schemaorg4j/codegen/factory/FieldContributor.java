package org.creativemines.schemaorg4j.codegen.factory;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;

import static org.creativemines.schemaorg4j.codegen.StringUtils.orLabelFromId;

import javax.lang.model.element.Modifier;

import org.creativemines.schemaorg4j.codegen.StringUtils;
import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.factory.types.FieldDeclarationRequirement;
import org.creativemines.schemaorg4j.codegen.factory.types.TypeFactory;
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
            FieldDeclarationRequirement type = typeFactory.build(property);

            try {
                if (type != null) {
                    FieldSpec spec = FieldSpec
                        .builder(type.getTypeName(),
                            orLabelFromId(property.getLabel(), property.getId()), Modifier.PRIVATE)
                        .addAnnotations(type.getFieldAnnotations())
                        .addJavadoc(StringUtils.escapeDollar(property.getComment()))
                        .build();
                    blueprint.addField(spec);
                } else {
                    LOGGER.warn("Could not determine type for field {} on {}, skipping",
                        property.getId(), schemaClass.getId());
                }
            } catch (IllegalArgumentException e) {
                LOGGER.warn("Could not create field '{}' (from {})", property.getLabel(),
                    property.getId());
                LOGGER.debug("Original error ", e);
            }
        });
    }
}
