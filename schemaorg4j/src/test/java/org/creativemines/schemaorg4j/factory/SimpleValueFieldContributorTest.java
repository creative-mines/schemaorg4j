package org.creativemines.schemaorg4j.factory;

import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.THING_ID;
import static org.junit.Assert.assertEquals;

import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;
import org.creativemines.schemaorg4j.codegen.domain.SchemaClassBuilder;
import org.creativemines.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
import org.creativemines.schemaorg4j.codegen.factory.SimpleValueFieldContributor;
import org.junit.Test;

public class SimpleValueFieldContributorTest {

    @Test
    public void contributesSimpleThingField() {
        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        new SimpleValueFieldContributor().contribute(thing(), blueprint);
        assertEquals(blueprint.getFields().get(0).name, "simpleValue");
    }

    private SchemaClass thing() {
        return new SchemaClassBuilder().setId(THING_ID).createSchemaClass();
    }
}
