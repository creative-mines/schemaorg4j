package com.schemaorg4j.factory;

import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.THING_ID;
import static org.junit.Assert.assertEquals;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaClassBuilder;
import com.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
import com.schemaorg4j.codegen.factory.SimpleThingContributor;
import org.junit.Test;

public class SimpleThingContributorTest {

    @Test
    public void contributesSimpleThingField() {
        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        new SimpleThingContributor().contribute(thing(), blueprint);
        assertEquals(blueprint.getFields().get(0).name, "simpleValue");
    }

    @Test
    public void contributesDefaultIsSimpleThingMethod() {
        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        new SimpleThingContributor().contribute(thing(), blueprint);
        assertEquals(blueprint.getMethods().get(0).name, "isSimpleThing");
    }

    private SchemaClass thing() {
        return new SchemaClassBuilder().setId(THING_ID).createSchemaClass();
    }
}
