package org.creativemines.schemaorg4j.factory;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.UTIL_PACKAGE;
import static org.junit.Assert.assertEquals;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import java.util.Collections;
import javax.lang.model.element.Modifier;

import org.creativemines.schemaorg4j.SchemaOrg4JConstants;
import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.factory.InterfaceContributor;
import org.creativemines.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
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
                DOMAIN_PACKAGE + ".CreativeWork");
    }

    @Test
    public void interfaceContainsALens() {
        SchemaClass creativeWork = schemaClass(
            "http://schema.org/CreativeWork", "CreativeWork", Collections.emptySet());
        SchemaClass book = schemaClass(
            "http://schema.org/Book", "Book",
            Collections.singleton("http://schema.org/CreativeWork"));
        SchemaGraph graph = schemaGraph(book, creativeWork);

        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        blueprint.addLensField(
            FieldSpec.builder(type(), "Author", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .build());
        new InterfaceContributor(graph).contribute(book, blueprint);

        assertEquals(blueprint.getTypes().get(0).fieldSpecs.get(0).name, "Author");

    }

    private TypeName type() {
        return ParameterizedTypeName.get(ClassName.get(UTIL_PACKAGE, "Lens"),
            ClassName.get(DOMAIN_PACKAGE, "Author"),
            ClassName.get("java.lang", "String"));
    }
}
