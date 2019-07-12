package com.schemaorg4j.codegen.factory.types;

import static com.schemaorg4j.codegen.StringUtils.orLabelFromId;
import static com.schemaorg4j.codegen.factory.types.MethodUtil.getGetter;
import static com.schemaorg4j.codegen.factory.types.MethodUtil.getSetter;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.factory.BlueprintContributor;
import com.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InheritedFieldContributor implements BlueprintContributor {

    private static final Logger LOGGER = LoggerFactory.getLogger(InheritedFieldContributor.class);

    private final SchemaGraph graph;
    private final TypeFactory factory;

    public InheritedFieldContributor(SchemaGraph graph, TypeFactory factory) {
        this.graph = graph;
        this.factory = factory;
    }

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        List<String> superclassIds = new ArrayList<>(schemaClass.getSubclassOfIds());

        while (!superclassIds.isEmpty()) {
            String currentId = superclassIds.remove(0);
            SchemaClass currentClass = graph.getClass(currentId);

            graph.getProperties(currentId).forEach(property -> {
                TypeName type = factory.build(property);
                if (type == null) {
                    LOGGER.warn("Could not resolve type for property {}, ignoring", property.getId());
                    return;
                }

                String label = orLabelFromId(property.getLabel(), property.getId());

                try {
                    FieldSpec field = FieldSpec.builder(type, label, Modifier.PRIVATE)
                        .build();
                    blueprint.addInheritedField(field);
                } catch (IllegalArgumentException e) {
                    LOGGER.warn("Unable to add field '{}' (from {}), applying disambiguator", property.getLabel(), property.getId());
                    LOGGER.debug("Original error", e);

                    FieldSpec field = FieldSpec.builder(type, "$" + label, Modifier.PRIVATE)
                        .build();
                    blueprint.addInheritedField(field);
                }
            });

            superclassIds.addAll(currentClass.getSubclassOfIds());
        }
    }
}
