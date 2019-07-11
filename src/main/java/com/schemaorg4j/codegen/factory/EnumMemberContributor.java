package com.schemaorg4j.codegen.factory;

import static com.schemaorg4j.codegen.constants.Schema4JConstants.DOMAIN_PACKAGE;
import static com.schemaorg4j.codegen.constants.Schema4JConstants.ENUM_PACKAGE;
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

public class EnumMemberContributor implements BlueprintContributor {

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

        ClassName typeName = ClassName.get(ENUM_PACKAGE, schemaClass.getLabel() + "EnumMembers");

        Builder enumBuilder = TypeSpec.enumBuilder(typeName);
        for (SchemaEnumMember member : graph.getEnumMembers(schemaClass.getId())) {
            enumBuilder.addEnumConstant(member.getLabel());
        }
        emittedTypes.add(enumBuilder.build());

        blueprint.addField(FieldSpec.builder(typeName, "enumMembers", Modifier.PRIVATE).build());
    }

    public List<TypeSpec> getEmittedTypes() {
        return emittedTypes;
    }
}
