package com.schemaorg4j.factory;

import static org.junit.Assert.assertEquals;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaClassBuilder;
import com.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
import com.schemaorg4j.codegen.factory.LensContributor;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import java.util.List;
import java.util.Objects;
import org.junit.Test;

public class LensContributorTest {

    @Test
    public void lensIsGeneratedForNativeField() {
        JavaPoetFileBlueprint blueprint = blueprint();
        new LensContributor().contribute(schemaClass(), blueprint);
        assertEquals(findField(blueprint.getLensFields(), "Author").type.toString(),
            "com.schemaorg4j.util.Lens<com.schemaorg4j.domain.Book, com.schemaorg4j.domain.Author>");
    }

    @Test
    public void lensIsInitializedWithCorrectCode() {
        JavaPoetFileBlueprint blueprint = blueprint();
        new LensContributor().contribute(schemaClass(), blueprint);
        assertEquals(findField(blueprint.getLensFields(), "Author").initializer.toString(),
            "new Lens<>(c -> c.getAuthor(), (c, fieldValue) -> { c.setAuthor(fieldValue); return c; })");
    }

    private FieldSpec findField(List<FieldSpec> fields, String name) {
        return fields.stream().filter(fieldSpec -> Objects.equals(fieldSpec.name, name)).findFirst()
            .orElse(null);
    }

    private JavaPoetFileBlueprint blueprint() {
        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        blueprint.addField(
            FieldSpec.builder(ClassName.get("com.schemaorg4j.domain", "Author"), "author").build());
        return blueprint;
    }

    private SchemaClass schemaClass() {
        return new SchemaClassBuilder().setLabel("Book").createSchemaClass();
    }

}
