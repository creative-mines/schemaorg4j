package org.creativemines.schemaorg4j.factory;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static org.junit.Assert.assertEquals;

import org.creativemines.schemaorg4j.codegen.domain.SchemaClassBuilder;
import org.creativemines.schemaorg4j.codegen.factory.ErrorObjectContributor;
import org.creativemines.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
import org.junit.Test;

public class ErrorObjectContributorTest {

    @Test
    public void shouldProvideFieldForErrorObjectOnThing() {
        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        new ErrorObjectContributor().contribute(new SchemaClassBuilder().setId("http://schema.org/Thing").createSchemaClass(), blueprint);
        assertEquals(blueprint.getFields().get(0).name, "schemaOrg4JErrors");
        assertEquals(blueprint.getFields().get(0).type.toString(), "java.util.List<" + DOMAIN_PACKAGE + ".error.SchemaOrg4JError>");
    }

    @Test
    public void shouldProvideFieldForErrorObjectOnNonThing() {
        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        new ErrorObjectContributor().contribute(new SchemaClassBuilder().setId("http://schema.org/Book").createSchemaClass(), blueprint);
        assertEquals(blueprint.getInheritedFields().get(0).name, "schemaOrg4JErrors");
        assertEquals(blueprint.getInheritedFields().get(0).type.toString(), "java.util.List<" + DOMAIN_PACKAGE + ".error.SchemaOrg4JError>");
    }
}
