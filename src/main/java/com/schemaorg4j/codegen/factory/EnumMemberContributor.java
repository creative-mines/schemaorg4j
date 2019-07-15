package com.schemaorg4j.codegen.factory;

import static com.schemaorg4j.codegen.StringUtils.orLabelFromId;
import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.ENUM_PACKAGE;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.ENUM_ID;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaEnumMember;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumMemberContributor implements BlueprintContributor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnumMemberContributor.class);
    private final SchemaGraph graph;
    private List<TypeSpec> emittedTypes;

    public EnumMemberContributor(SchemaGraph graph) {
        this.graph = graph;
        this.emittedTypes = new ArrayList<>();
    }

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        if (!schemaClass.getSubclassOfIds().contains(ENUM_ID)) {
            return;
        }

        if (graph.getEnumMembers(schemaClass.getId()).isEmpty()) {
            LOGGER.warn("Enum {} had 0 members, skipping", schemaClass.getId());
            return;
        }

        ClassName typeName = ClassName.get(ENUM_PACKAGE, schemaClass.getLabel() + "EnumMembers");

        Builder enumBuilder = TypeSpec.enumBuilder(typeName).addModifiers(Modifier.PUBLIC);
        for (SchemaEnumMember member : graph.getEnumMembers(schemaClass.getId())) {
            try {
                enumBuilder.addEnumConstant(orLabelFromId(member.getLabel(), member.getId()));
            } catch (IllegalArgumentException e) {
                LOGGER.warn("Could not add enum constant '{}' (from {})", member.getLabel(),
                    member.getId());
                LOGGER.debug("Original error", e);
            }
        }
        emittedTypes.add(enumBuilder.build());

        blueprint.addField(FieldSpec.builder(typeName, "enumMembers", Modifier.PRIVATE).build());
    }

    public List<TypeSpec> getEmittedTypes() {
        return emittedTypes;
    }
}
