package org.creativemines.schemaorg4j.factory.types;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.creativemines.schemaorg4j.SchemaOrg4JConstants;
import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;
import org.creativemines.schemaorg4j.codegen.domain.SchemaClassBuilder;
import org.creativemines.schemaorg4j.codegen.domain.SchemaDataType;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.domain.SchemaPropertyBuilder;
import org.creativemines.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
import org.creativemines.schemaorg4j.codegen.factory.types.InheritedFieldContributor;
import org.creativemines.schemaorg4j.codegen.factory.types.SimpleTypeFactory;
import org.junit.Test;

public class InheritedFieldContributorTest {

    @Test
    public void shouldAddFieldsFromASuperType() {
        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        SchemaGraph schemaGraph = schemaGraph();
        new InheritedFieldContributor(schemaGraph, new SimpleTypeFactory(schemaGraph))
            .contribute(schemaClass(), blueprint);

        assertEquals(blueprint.getInheritedFields().get(0).name, "fromCreativeWork");
        assertEquals(blueprint.getInheritedFields().get(0).type.toString(), SchemaOrg4JConstants.DATATYPES_PACKAGE + ".Text");
    }

    @Test
    public void shouldAddFieldsFromASuperSuperType() {
        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        SchemaGraph schemaGraph = schemaGraph();
        new InheritedFieldContributor(schemaGraph, new SimpleTypeFactory(schemaGraph))
            .contribute(schemaClass(), blueprint);

        assertEquals(blueprint.getInheritedFields().get(3).name, "fromThing");
        assertEquals(blueprint.getInheritedFields().get(3).type.toString(), SchemaOrg4JConstants.DATATYPES_PACKAGE + ".Text");
    }


    public SchemaClass schemaClass() {
        return new SchemaClassBuilder()
            .setId("http://schema.org/Book")
            .setSubclassOfIds(Collections.singleton("http://schema.org/CreativeWork"))
            .createSchemaClass();
    }

    public SchemaGraph schemaGraph() {
        SchemaGraph g = new SchemaGraph();

        SchemaClass creativeWork = new SchemaClassBuilder()
            .setId("http://schema.org/CreativeWork")
            .setSubclassOfIds(Collections.singleton("http://schema.org/Thing"))
            .createSchemaClass();

        SchemaClass thing = new SchemaClassBuilder()
            .setId("http://schema.org/Thing")
            .createSchemaClass();

        g.addClass(schemaClass());
        g.addClass(creativeWork);
        g.addClass(thing);

        g.addProperty(new SchemaPropertyBuilder().setId("a")
            .setDomainIncludesIds(Collections.singleton("http://schema.org/CreativeWork"))
            .setLabel("fromCreativeWork")
            .setRangeIncludesIds(Collections.singleton(SchemaDataType.TEXT.getId()))
            .createSchemaProperty());

        g.addProperty(new SchemaPropertyBuilder().setId("b")
            .setDomainIncludesIds(Collections.singleton("http://schema.org/Thing"))
            .setLabel("fromThing")
            .setRangeIncludesIds(Collections.singleton(SchemaDataType.TEXT.getId()))
            .createSchemaProperty());

        return g;
    }
}
