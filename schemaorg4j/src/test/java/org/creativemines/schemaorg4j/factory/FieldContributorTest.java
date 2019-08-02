package org.creativemines.schemaorg4j.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import java.util.HashSet;
import javax.lang.model.element.Modifier;

import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;
import org.creativemines.schemaorg4j.codegen.domain.SchemaClassBuilder;
import org.creativemines.schemaorg4j.codegen.domain.SchemaDataType;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.domain.SchemaProperty;
import org.creativemines.schemaorg4j.codegen.domain.SchemaPropertyBuilder;
import org.creativemines.schemaorg4j.codegen.factory.FieldContributor;
import org.creativemines.schemaorg4j.codegen.factory.JavaPoetFileBlueprint;
import org.creativemines.schemaorg4j.codegen.factory.types.FieldDeclarationRequirement;
import org.junit.Test;

public class FieldContributorTest {

    @Test
    public void privateFieldCanBeCreatedWithBasicDataType() {
        SchemaGraph graph = new SchemaGraph();

        SchemaClass schemaClass = new SchemaClassBuilder().setId("test").setLabel("Test")
            .createSchemaClass();
        SchemaProperty schemaProperty = new SchemaPropertyBuilder().setLabel("pageCount")
            .setDomainIncludesIds(new HashSet<String>() {{
                add("test");
            }}).setRangeIncludesIds(new HashSet<String>() {{
                add(SchemaDataType.INTEGER.getId());
            }}).createSchemaProperty();

        graph.addClass(schemaClass);
        graph.addProperty(schemaProperty);

        JavaPoetFileBlueprint blueprint = new JavaPoetFileBlueprint();

        FieldContributor fieldContributor = new FieldContributor(graph,
            property -> new FieldDeclarationRequirement(ClassName.INT.box()));
        fieldContributor.contribute(schemaClass, blueprint);

        FieldSpec firstField = blueprint.getFields().get(0);

        assertEquals(firstField.name, "pageCount");
        assertEquals(firstField.type.toString(), "java.lang.Integer");
        assertTrue(firstField.modifiers.contains(Modifier.PRIVATE));
    }
}
