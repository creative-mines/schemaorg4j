package com.schemaorg4j.codegen.factory;

import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static com.schemaorg4j.codegen.factory.types.MethodUtil.getGetter;
import static com.schemaorg4j.codegen.factory.types.MethodUtil.getSetter;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.Modifier;

public class ImplementationContributor implements BlueprintContributor {

    private final SchemaGraph graph;

    public ImplementationContributor(SchemaGraph graph) {
        this.graph = graph;
    }

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {

        String label = schemaClass.getLabel() + "Impl";
        if (schemaClass.getLabel().matches("^[0-9].*")) {
            label = "$" + label;
        }

        Builder builder = TypeSpec
            .classBuilder(label)
            .addModifiers(Modifier.PUBLIC)
            .addSuperinterface(ClassName.get(DOMAIN_PACKAGE, schemaClass.getLabel()));

        builder.addFields(blueprint.getFields());
        builder.addFields(blueprint.getInheritedFields());
        builder.addMethods(blueprint.getMethods().stream()
            .filter(method -> !method.modifiers.contains(Modifier.DEFAULT)).collect(
                Collectors.toList()));
        builder.addMethods(generateInheritedAccessorsAndMutators(blueprint));

        blueprint.addType(builder.build());
    }

    private Iterable<MethodSpec> generateInheritedAccessorsAndMutators(
        JavaPoetFileBlueprint blueprint) {
        return blueprint.getInheritedFields()
            .stream()
            .map(field -> Stream.of(
                getGetter(field).stream().map(getter -> getter.toBuilder().addAnnotation(Override.class).build()),
                getSetter(field).stream().map(setter -> setter.toBuilder().addAnnotation(Override.class).build())
            ))
            .flatMap(s1 -> s1.flatMap(s2 -> s2))
            .collect(Collectors.toList());

    }
}
