package org.creativemines.schemaorg4j.codegen.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static org.creativemines.schemaorg4j.codegen.factory.types.MethodUtil.getGetter;
import static org.creativemines.schemaorg4j.codegen.factory.types.MethodUtil.getSetter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.Modifier;

import org.creativemines.schemaorg4j.annotations.SchemaOrg4JNoGetterSetter;
import org.creativemines.schemaorg4j.codegen.StringUtils;
import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;

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
            .addSuperinterface(ClassName.get(DOMAIN_PACKAGE, schemaClass.getLabel()))
            .addJavadoc(StringUtils.escapeDollar(schemaClass.getComment()));

        builder.addFields(blueprint.getFields());
        builder.addFields(blueprint.getInheritedFields());
        builder.addMethods(blueprint.getMethods().stream()
            .filter(method -> !method.modifiers.contains(Modifier.DEFAULT)).collect(
                Collectors.toList()));
        builder.addMethods(generateInheritedAccessorsAndMutators(blueprint).stream().filter(
            methodToAdd -> blueprint.getMethods().stream()
                .noneMatch(existingMethod -> Objects.equals(methodToAdd.name, existingMethod.name))
        ).collect(Collectors.toList()));

        blueprint.addType(builder.build());
    }

    private List<MethodSpec> generateInheritedAccessorsAndMutators(
        JavaPoetFileBlueprint blueprint) {
        return blueprint.getInheritedFields()
            .stream()
            .filter(field -> field.annotations.stream().noneMatch(annotationSpec -> annotationSpec.type.equals(ClassName.get(
                SchemaOrg4JNoGetterSetter.class))))
            .map(field -> Stream.of(
                getGetter(field).stream()
                    .map(getter -> getter.toBuilder().addAnnotation(Override.class).build()),
                getSetter(field).stream()
                    .map(setter -> setter.toBuilder().addAnnotation(Override.class).build())
            ))
            .flatMap(s1 -> s1.flatMap(s2 -> s2))
            .collect(Collectors.toList());

    }
}
