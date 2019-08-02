package org.creativemines.schemaorg4j.factory;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.util.Objects;

import org.creativemines.schemaorg4j.SchemaOrg4JConstants;
import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;
import org.creativemines.schemaorg4j.codegen.domain.SchemaClassBuilder;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.factory.ImplementationContributor;
import org.creativemines.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
import org.junit.Test;

public class ImplementationContributorTest {

    @Test
    public void shouldGenerateTheCorrectImplementationClassName() {
        JavaPoetFileBlueprint blueprint = blueprint();
        new ImplementationContributor(schemaGraph()).contribute(schemaClass(), blueprint);
        assertEquals(blueprint.getTypes().get(0).name, "BookImpl");
    }

    @Test
    public void shouldImplementTheCorrectInterface() {
        JavaPoetFileBlueprint blueprint = blueprint();
        new ImplementationContributor(schemaGraph()).contribute(schemaClass(), blueprint);
        assertEquals(blueprint.getTypes().get(0).superinterfaces.get(0).toString(), DOMAIN_PACKAGE + ".Book");
    }

    @Test
    public void shouldHaveInheritedSetters() {
        JavaPoetFileBlueprint blueprint = blueprint();
        new ImplementationContributor(schemaGraph()).contribute(schemaClass(), blueprint);
        assertNotNull(getMethod(blueprint.getTypes().get(0), "setFromThing"));
    }

    @Test
    public void shouldHaveInheritedGetters() {
        JavaPoetFileBlueprint blueprint = blueprint();
        new ImplementationContributor(schemaGraph()).contribute(schemaClass(), blueprint);
        assertNotNull(getMethod(blueprint.getTypes().get(0), "getFromThing"));
    }

    @Test
    public void shouldHaveInheritedFields() {
        JavaPoetFileBlueprint blueprint = blueprint();
        new ImplementationContributor(schemaGraph()).contribute(schemaClass(), blueprint);
        assertEquals(blueprint.getTypes().get(0).fieldSpecs.get(1).name, "fromThing");
    }

    @Test
    public void shouldHaveOverrideAnnotationsOnMethods() {
        JavaPoetFileBlueprint blueprint = blueprint();
        new ImplementationContributor(schemaGraph()).contribute(schemaClass(), blueprint);
        assertEquals(getMethod(blueprint.getTypes().get(0), "getFromThing").annotations.get(0).toString(), "@java.lang.Override");
    }

    @Test
    public void shouldHaveRegularFields() {
        JavaPoetFileBlueprint blueprint = blueprint();
        new ImplementationContributor(schemaGraph()).contribute(schemaClass(), blueprint);
        assertEquals(blueprint.getTypes().get(0).fieldSpecs.get(0).name, "fromBook");
    }

    @Test
    public void shouldHaveRegularSetters() {
        JavaPoetFileBlueprint blueprint = blueprint();
        new ImplementationContributor(schemaGraph()).contribute(schemaClass(), blueprint);
        assertNotNull(getMethod(blueprint.getTypes().get(0), "setFromBook"));
    }

    @Test
    public void shouldHaveRegularGetters() {
        JavaPoetFileBlueprint blueprint = blueprint();
        new ImplementationContributor(schemaGraph()).contribute(schemaClass(), blueprint);
        assertNotNull(getMethod(blueprint.getTypes().get(0), "getFromBook"));
    }

    private SchemaClass schemaClass() {
        return new SchemaClassBuilder().setId("Book").setLabel("Book").createSchemaClass();
    }

    private SchemaGraph schemaGraph() {
        SchemaGraph graph = new SchemaGraph();
        graph.addClass(schemaClass());
        return graph;
    }

    private JavaPoetFileBlueprint blueprint() {
        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        blueprint.addMethod(MethodSpec.methodBuilder("getFromBook").build());
        blueprint.addMethod(MethodSpec.methodBuilder("setFromBook").build());
        blueprint.addField(FieldSpec.builder(TypeName.INT.box(), "fromBook").build());
        blueprint.addInheritedField(FieldSpec.builder(TypeName.INT.box(), "fromThing").build());
        return blueprint;
    }

    private MethodSpec getMethod(TypeSpec typeSpec, String methodName) {
        return typeSpec.methodSpecs.stream().filter(spec -> Objects.equals(methodName, spec.name)).findAny().orElse(null);
    }

}
