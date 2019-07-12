package com.schemaorg4j.codegen.factory.types;

import static com.schemaorg4j.codegen.StringUtils.decapitalize;
import static com.schemaorg4j.codegen.StringUtils.orLabelFromId;
import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.DOMAIN_PACKAGE;
import static com.schemaorg4j.codegen.factory.types.MethodUtil.getGetter;
import static com.schemaorg4j.codegen.factory.types.MethodUtil.getSetter;

import com.schemaorg4j.codegen.domain.SchemaDataType;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.lang.model.element.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiTypeProducingTypeFactory implements TypeFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiTypeProducingTypeFactory.class);

    private final TypeFactory collaborator;
    private final List<TypeSpec> emittedTypes;
    private final SchemaGraph graph;

    public MultiTypeProducingTypeFactory(TypeFactory collaborator, SchemaGraph graph) {
        this.collaborator = collaborator;
        this.emittedTypes = new ArrayList<>();
        this.graph = graph;
    }

    @Override
    public TypeName build(SchemaProperty property) {
        TypeName name = collaborator.build(property);
        if (mustBeMultiType(property)) {
            emitMultiType(property, name);
        }
        return name;
    }

    private void emitMultiType(SchemaProperty property, TypeName name) {
        Builder builder = TypeSpec
            .classBuilder(ClassName.bestGuess(name.toString()).simpleName())
            .addModifiers(Modifier.PUBLIC);

        property.getRangeIncludesIds().stream().sorted().forEach(id -> {
            String variableName = decapitalize(orLabelFromId(null, id));
            String classLabel = graph.getClass(id).getLabel();

            FieldSpec field = getFieldSpec(classLabel, variableName);

            builder.addField(field);
            builder.addMethod(getSetter(field));
            builder.addMethod(getGetter(field));
        });

        emittedTypes.add(builder.build());
    }

    private FieldSpec getFieldSpec(String classLabel, String variableName) {
        try {
            return FieldSpec
                .builder(ClassName.get(DOMAIN_PACKAGE, classLabel), variableName, Modifier.PRIVATE)
                .build();
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Variable name {} was invalid, applying disambiguator", variableName);
            return FieldSpec
                .builder(ClassName.get(DOMAIN_PACKAGE, classLabel), "$" + variableName, Modifier.PRIVATE)
                .build();
        }
    }

    private boolean mustBeMultiType(SchemaProperty property) {
        boolean couldBeMultiType = property.getRangeIncludesIds().size() > 1 && (
            property.getRangeIncludesIds().size() > 2 || property.getRangeIncludesIds().stream()
                .noneMatch(id -> Objects.equals(id, SchemaDataType.TEXT.getId())));

        if (couldBeMultiType) {
            return property
                .getRangeIncludesIds()
                .stream()
                .allMatch(id -> graph.getClass(id) != null);
        }

        return false;
    }

    public List<TypeSpec> getEmittedTypes() {
        return emittedTypes;
    }
}
