package org.creativemines.schemaorg4j.factory;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import javax.lang.model.element.Modifier;

import org.creativemines.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
import org.creativemines.schemaorg4j.codegen.factory.MethodContributor;
import org.junit.Test;

public class MethodContributorTest {

    public JavaPoetFileBlueprint blueprint() {
        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();
        blueprint.addField(
            FieldSpec.builder(ClassName.get(DOMAIN_PACKAGE, "Book"), "book", Modifier.PRIVATE)
                .build());
        return blueprint;
    }

    @Test
    public void gettersAreGeneratedForAllFields() {
        JavaPoetFileBlueprint blueprint = blueprint();
        new MethodContributor(null).contribute(null, blueprint);
        MethodSpec getter = blueprint.getMethods().stream()
            .filter(spec -> spec.name.startsWith("get")).findFirst().get();

        assertEquals(getter.name, "getBook");
        assertTrue(getter.parameters.isEmpty());
        assertEquals(getter.returnType, ClassName.get(DOMAIN_PACKAGE, "Book"));
        assertTrue(getter.code.toString().contains("return book"));
    }

    @Test
    public void settersAreGeneratedForAllFields() {
        JavaPoetFileBlueprint blueprint = blueprint();
        new MethodContributor(null).contribute(null, blueprint);
        MethodSpec setter = blueprint.getMethods().stream()
            .filter(spec -> spec.name.startsWith("set")).findFirst().get();

        assertEquals(setter.name, "setBook");
        assertEquals(setter.parameters.get(0).type, ClassName.get(DOMAIN_PACKAGE, "Book"));
        assertEquals(setter.parameters.get(0).name, "book");
        assertEquals(setter.returnType, TypeName.VOID);
        assertTrue(setter.code.toString().contains("this.book = book"));
    }
}
