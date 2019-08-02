package org.creativemines.schemaorg4j.codegen.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static org.creativemines.schemaorg4j.codegen.StringUtils.escapeDollar;
import static org.creativemines.schemaorg4j.codegen.StringUtils.orLabelFromId;

import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

import org.creativemines.schemaorg4j.codegen.StringUtils;
import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterfaceContributor implements BlueprintContributor {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceContributor.class);

    private final SchemaGraph graph;

    public InterfaceContributor(SchemaGraph graph) {
        this.graph = graph;
    }

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        try {
            String label = orLabelFromId(schemaClass.getLabel(), schemaClass.getId());
            if (label.matches("^[0-9].*")) {
                label = "$" + label;
            }

            Builder builder = TypeSpec
                .interfaceBuilder(ClassName.get(DOMAIN_PACKAGE, label))
                .addModifiers(Modifier.PUBLIC);

            addMethods(builder, blueprint);
            addSuperInterfaces(builder, schemaClass);
            builder.addFields(blueprint.getLensFields())
                .addJavadoc(escapeDollar(schemaClass.getComment()));

            blueprint.addType(builder.build());
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Could not create interface '{}' (from {})", schemaClass.getLabel(),
                schemaClass.getId());
            LOGGER.debug("Original error", e);
        }
    }

    private void addSuperInterfaces(Builder builder, SchemaClass schemaClass) {
        schemaClass.getSubclassOfIds()
            .stream()
            .map(graph::getClass)
            .map(SchemaClass::getLabel)
            .sorted()
            .forEach(superTypeName -> builder
                .addSuperinterface(ClassName.get(DOMAIN_PACKAGE, superTypeName)));
    }

    private void addMethods(Builder builder, JavaPoetFileBlueprint blueprint) {
        builder.addMethods(blueprint.getMethods().stream().map(methodSpec -> {
            MethodSpec.Builder methodSpecBuilder = MethodSpec
                .methodBuilder(methodSpec.name)
                .returns(methodSpec.returnType)
                .addJavadoc(StringUtils.escapeDollar(methodSpec.javadoc != null ? methodSpec.javadoc.toString() : ""))
                .addParameters(methodSpec.parameters);

            if (methodSpec.modifiers.contains(Modifier.DEFAULT)) {
                methodSpecBuilder.addModifiers(Modifier.DEFAULT, Modifier.PUBLIC).addCode(methodSpec.code);
            } else {
                methodSpecBuilder.addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC);
            }

            return methodSpecBuilder.build();
        }).collect(Collectors.toList()));
    }
}
