package com.schemaorg4j.codegen.factory.types;

import static com.schemaorg4j.codegen.StringUtils.orLabelFromId;
import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.ENUM_PACKAGE;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.ENUM_ID;
import static com.schemaorg4j.codegen.factory.types.MethodUtil.getGetter;
import static com.schemaorg4j.codegen.factory.types.MethodUtil.getSetter;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.factory.BlueprintContributor;
import com.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
import com.schemaorg4j.codegen.jsonld.Util;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
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

            getInheritedFieldSpec(currentId).forEach(blueprint::addInheritedField);
            getInheritedEnumFields(currentId).ifPresent(blueprint::addInheritedField);
            blueprint.addInheritedField(Util.generateNextField(DOMAIN_PACKAGE, currentClass.getLabel()));

            superclassIds.addAll(currentClass.getSubclassOfIds());
        }
    }

    private Optional<FieldSpec> getInheritedEnumFields(String currentId) {
        SchemaClass superClass = graph.getClass(currentId);
        if (superClass.getSubclassOfIds().contains(ENUM_ID) && !graph.getEnumMembers(superClass.getId()).isEmpty()) {
            SchemaClass schemaClass = graph.getClass(currentId);
            ClassName typeName = ClassName.get(ENUM_PACKAGE, schemaClass.getLabel() + "EnumMembers");
            return Optional.of(FieldSpec.builder(typeName, "enumMembers", Modifier.PRIVATE).build());
        }
        return Optional.empty();
    }

    private Iterable<FieldSpec> getInheritedFieldSpec(String currentId) {
        return graph.getProperties(currentId).stream().map(property -> {
            TypeName type = factory.build(property);
            if (type == null) {
                LOGGER.warn("Could not resolve type for property {}, ignoring", property.getId());
                return null;
            }

            String label = orLabelFromId(property.getLabel(), property.getId());

            try {
                return FieldSpec.builder(type, label, Modifier.PRIVATE)
                    .build();
            } catch (IllegalArgumentException e) {
                LOGGER.warn("Unable to add field '{}' (from {}), applying disambiguator", property.getLabel(), property.getId());
                LOGGER.debug("Original error", e);

                return FieldSpec.builder(type, "$" + label, Modifier.PRIVATE)
                    .build();
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
