package com.schemaorg4j.factory;

import static org.junit.Assert.assertEquals;

import com.schemaorg4j.codegen.domain.SchemaClassBuilder;
import com.schemaorg4j.codegen.factory.ErrorObjectContributor;
import com.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
import org.junit.Test;

public class ErrorObjectContributorTest {

    @Test
    public void shouldProvideFieldForErrorObject() {
        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        new ErrorObjectContributor().contribute(new SchemaClassBuilder().setId("http://schema.org/Thing").createSchemaClass(), blueprint);
        assertEquals(blueprint.getFields().get(0).name, "error");
        assertEquals(blueprint.getFields().get(0).type.toString(), "com.schemaorg4j.domain.error.SchemaOrg4JError");
    }
}
