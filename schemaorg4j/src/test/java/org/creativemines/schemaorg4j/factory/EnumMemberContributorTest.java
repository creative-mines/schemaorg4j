package org.creativemines.schemaorg4j.factory;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.ENUM_PACKAGE;
import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.ENUM_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.squareup.javapoet.TypeSpec;
import java.util.Collections;
import java.util.Map;

import org.creativemines.schemaorg4j.SchemaOrg4JConstants;
import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;
import org.creativemines.schemaorg4j.codegen.domain.SchemaEnumMember;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.factory.EnumMemberContributor;
import org.creativemines.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
import org.junit.Test;

public class EnumMemberContributorTest extends ContributorTest {

    private SchemaClass schemaClass() {
        return schemaClass("http://schema.org/BookFormatType", "BookFormatType",
            Collections.singleton(ENUM_ID));
    }

    private SchemaGraph schemaGraph() {
        SchemaClass schemaClass = schemaClass();
        SchemaGraph graph = schemaGraph(schemaClass);
        graph.addEnumMember(
            new SchemaEnumMember("http://schema.org/AudioBookFormat", schemaClass.getId(),
                "AudioBookFormat"));
        graph
            .addEnumMember(
                new SchemaEnumMember("http://schema.org/EBookFormat", schemaClass.getId(),
                    "EBookFormat"));
        return graph;

    }

    @Test
    public void shouldGenerateAFieldWithTheCorrectEnumTypeAndLabel() {
        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        new EnumMemberContributor(schemaGraph()).contribute(schemaClass(), blueprint);
        assertEquals(blueprint.getFields().get(0).type.toString(),
            ENUM_PACKAGE + ".BookFormatTypeEnumMembers");
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
