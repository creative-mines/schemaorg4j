package com.schemaorg4j.factory;

import static org.junit.Assert.assertEquals;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.factory.InterfaceContributor;
import com.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
import com.squareup.javapoet.MethodSpec;
import java.util.Collections;
import javax.lang.model.element.Modifier;
import org.junit.Test;

public class InterfaceContributorTest extends ContributorTest {

    @Test
    public void interfaceContributorAddsRightType() {
        SchemaClass creativeWork = schemaClass(
            "http://schema.org/CreativeWork", "CreativeWork", Collections.emptySet());
        SchemaClass book = schemaClass(
            "http://schema.org/Book", "Book",
            Collections.singleton("http://schema.org/CreativeWork"));
        SchemaGraph graph = schemaGraph(book, creativeWork);

        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        new InterfaceContributor(graph).contribute(book, blueprint);

        assertEquals(blueprint.getTypes().get(0).name, "Book");
    }

    @Test
    public void interfacesHaveMethods() {
        SchemaClass creativeWork = schemaClass(
            "http://schema.org/CreativeWork", "CreativeWork", Collections.emptySet());
        SchemaClass book = schemaClass(
            "http://schema.org/Book", "Book",
            Collections.singleton("http://schema.org/CreativeWork"));
        SchemaGraph graph = schemaGraph(book, creativeWork);

        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        blueprint.addMethod(MethodSpec
            .methodBuilder("getBook")
            .build());
        new InterfaceContributor(graph).contribute(book, blueprint);

        assertEquals(blueprint.getTypes().get(0).methodSpecs.get(0).name, "getBook");
    }

    @Test
    public void interfacesHaveCorrectSuperClasses() {
        SchemaClass creativeWork = schemaClass(
            "http://schema.org/CreativeWork", "CreativeWork", Collections.emptySet());
        SchemaClass book = schemaClass(
            "http://schema.org/Book", "Book",
            Collections.singleton("http://schema.org/CreativeWork"));
        SchemaGraph graph = schemaGraph(book, creativeWork);

        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        new InterfaceContributor(graph).contribute(book, blueprint);

        assertEquals(blueprint.getTypes().get(0).superinterfaces.get(0).toString(),
            "com.schemaorg4j.domain.CreativeWork");
    }

    @Test
    public void interfaceContainsALens() {

    }
}
