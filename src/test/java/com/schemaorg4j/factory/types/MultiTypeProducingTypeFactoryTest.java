package com.schemaorg4j.factory.types;

import static com.schemaorg4j.codegen.constants.Schema4JConstants.DOMAIN_PACKAGE;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import com.schemaorg4j.codegen.domain.SchemaClassBuilder;
import com.schemaorg4j.codegen.domain.SchemaDataType;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.schemaorg4j.codegen.domain.SchemaPropertyBuilder;
import com.schemaorg4j.codegen.factory.types.MultiTypeProducingTypeFactory;
import com.schemaorg4j.codegen.factory.types.SimpleTypeFactory;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;
import org.junit.Test;

public class MultiTypeProducingTypeFactoryTest {

    @Test
    public void willNotProduceAMultiTypeForOrTypes() {
        MultiTypeProducingTypeFactory multiTypeFactory = multiTypeFactory();
        multiTypeFactory.build(orSchemaProperty());
        assertTrue(multiTypeFactory.getEmittedTypes().isEmpty());
    }

    @Test
    public void emittedMultiTypeHasCorrectName() {
        MultiTypeProducingTypeFactory multiTypeFactory = multiTypeFactory();
        multiTypeFactory.build(multiTypeProperty());
        assertEquals(multiTypeFactory.getEmittedTypes().get(0).name, "AudioOrBook");
    }

    @Test
    public void emittedMultiTypeHasCorrectFields() {
        MultiTypeProducingTypeFactory multiTypeFactory = multiTypeFactory();
        multiTypeFactory.build(multiTypeProperty());

        TypeSpec spec = multiTypeFactory.getEmittedTypes().get(0);

        assertEquals(spec.fieldSpecs.get(0).name, "audio");
        assertEquals(spec.fieldSpecs.get(0).type.toString(), "com.schemaorg4j.domain.Audio");
        assertTrue(spec.fieldSpecs.get(0).hasModifier(Modifier.PRIVATE));
        assertEquals(spec.fieldSpecs.get(1).name, "book");
        assertEquals(spec.fieldSpecs.get(1).type.toString(), "com.schemaorg4j.domain.Book");
        assertTrue(spec.fieldSpecs.get(1).hasModifier(Modifier.PRIVATE));
    }

    @Test
    public void emittedMultiTypeHasCorrectSetters() {
        MultiTypeProducingTypeFactory multiTypeFactory = multiTypeFactory();
        multiTypeFactory.build(multiTypeProperty());

        TypeSpec spec = multiTypeFactory.getEmittedTypes().get(0);

        List<MethodSpec> setters = getSetters(spec);
        assertEquals(setters.get(0).name, "setAudio");
        assertEquals(setters.get(0).returnType, TypeName.VOID);
        assertEquals(setters.get(0).parameters.get(0).name, "audio");
        assertTrue(setters.get(0).code.toString().contains("this.audio = audio"));
    }

    @Test
    public void emittedMultiTypeHasCorrectGetters() {
        MultiTypeProducingTypeFactory multiTypeFactory = multiTypeFactory();
        multiTypeFactory.build(multiTypeProperty());

        TypeSpec spec = multiTypeFactory.getEmittedTypes().get(0);

        List<MethodSpec> getters = getGetters(spec);

        assertEquals(getters.get(0).name, "getAudio");
        assertEquals(getters.get(0).returnType, ClassName.get(DOMAIN_PACKAGE, "Audio"));
        assertTrue(getters.get(0).parameters.isEmpty());
        assertTrue(getters.get(0).code.toString().contains("return audio"));
    }

    private SchemaProperty orSchemaProperty() {
        return new SchemaPropertyBuilder().setId("http://schema.org/author").setRangeIncludesIds(
            new HashSet<String>() {{
                add(SchemaDataType.TEXT.getId());
                add(SchemaDataType.INTEGER.getId());
            }}).createSchemaProperty();
    }

    private SchemaProperty multiTypeProperty() {
        return new SchemaPropertyBuilder().setId("http://schema.org/subject").setRangeIncludesIds(
            new HashSet<String>() {{
                add("http://schema.org/Book");
                add("http://schema.org/Audio");
            }}).createSchemaProperty();
    }

    private SchemaGraph schemaGraph() {
        SchemaGraph graph = new SchemaGraph();
        graph.addClass(new SchemaClassBuilder().setId("http://schema.org/Book").setLabel("Book").createSchemaClass());
        graph.addClass(new SchemaClassBuilder().setId("http://schema.org/Audio").setLabel("Audio").createSchemaClass());
        return graph;
    }

    private MultiTypeProducingTypeFactory multiTypeFactory() {
        SchemaGraph graph = schemaGraph();
        return new MultiTypeProducingTypeFactory(new SimpleTypeFactory(graph), graph);
    }

    private List<MethodSpec> getSetters(TypeSpec spec) {
        return spec.methodSpecs.stream().filter(mSpec -> mSpec.name.startsWith("set")).collect(
            Collectors.toList());
    }

    private List<MethodSpec> getGetters(TypeSpec spec) {
        return spec.methodSpecs.stream().filter(mSpec -> mSpec.name.startsWith("get")).collect(
            Collectors.toList());
    }

}
