package org.creativemines.schemaorg4j.factory;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.UTIL_PACKAGE;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Objects;

import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;
import org.creativemines.schemaorg4j.codegen.domain.SchemaClassBuilder;
import org.creativemines.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
import org.creativemines.schemaorg4j.codegen.factory.LensContributor;
import org.junit.Test;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;

public class LensContributorTest {

    @Test
    public void lensIsGeneratedForNativeField() {
        JavaPoetFileBlueprint blueprint = blueprint();
        new LensContributor().contribute(schemaClass(), blueprint);
        assertEquals(findField(blueprint.getLensFields(), "Author").type.toString(),
            UTIL_PACKAGE + ".Lens<" + DOMAIN_PACKAGE + ".Book, " + DOMAIN_PACKAGE + ".Author>");
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
            FieldSpec.builder(ClassName.get(DOMAIN_PACKAGE, "Author"), "author").build());
        return blueprint;
    }

    private SchemaClass schemaClass() {
        return new SchemaClassBuilder().setLabel("Book").createSchemaClass();
    }

}
