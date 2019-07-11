package com.schemaorg4j.factory;

import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.ENUM_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaClassBuilder;
import com.schemaorg4j.codegen.domain.SchemaEnumMember;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.factory.EnumMemberContributor;
import com.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
import com.squareup.javapoet.TypeSpec;
import java.util.Collections;
import java.util.Map;
import org.junit.Test;

public class EnumMemberContributorTest {

    private SchemaClass schemaClass() {
        return new SchemaClassBuilder().setId("http://schema.org/BookFormatType")
            .setLabel("BookFormatType").setSubclassOfIds(
                Collections.singleton(ENUM_ID)).createSchemaClass();
    }

    private SchemaGraph schemaGraph() {
        SchemaClass schemaClass = schemaClass();
        SchemaGraph graph = new SchemaGraph();
        graph.addClass(schemaClass);
        graph.addEnumMember(
            new SchemaEnumMember("http://schema.org/AudioBookFormat", schemaClass.getId(), "AudioBookFormat"));
        graph
            .addEnumMember(new SchemaEnumMember("http://schema.org/EBookFormat", schemaClass.getId(), "EBookFormat"));
        return graph;
    }

    @Test
    public void shouldGenerateAFieldWithTheCorrectEnumTypeAndLabel() {
        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        new EnumMemberContributor(schemaGraph()).contribute(schemaClass(), blueprint);
        assertEquals(blueprint.getFields().get(0).type.toString(), "com.schemaorg4j.domain.enums.BookFormatTypeEnumMembers");
    }

    @Test
    public void shouldGenerateAnEmittedEnumType() {
        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        EnumMemberContributor contributor = new EnumMemberContributor(schemaGraph());
        contributor.contribute(schemaClass(), blueprint);
        assertEquals(contributor.getEmittedTypes().get(0).name, "BookFormatTypeEnumMembers");
    }

    @Test
    public void emittedEnumTypeShouldHaveTheRightMembers() {
        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        EnumMemberContributor contributor = new EnumMemberContributor(schemaGraph());
        contributor.contribute(schemaClass(), blueprint);
        Map<String, TypeSpec> enumConstants = contributor
            .getEmittedTypes().get(0).enumConstants;
        assertTrue(enumConstants.containsKey("AudioBookFormat"));
        assertTrue(enumConstants.containsKey("EBookFormat"));
    }
}
