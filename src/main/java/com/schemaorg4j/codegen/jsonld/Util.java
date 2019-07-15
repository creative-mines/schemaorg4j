package com.schemaorg4j.codegen.jsonld;

import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.COMMENT;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.LABEL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaDataType;
import com.schemaorg4j.codegen.domain.SchemaEnumMember;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.lang.model.element.Modifier;

/**
 * Package private Util class specifically for json deserializers
 */
public class Util {

    private static ObjectMapper OBJECT_MAPPER_INSTNACE;

    public static ObjectMapper getObjectMapperInstnace() {
        if (OBJECT_MAPPER_INSTNACE == null) {
            OBJECT_MAPPER_INSTNACE = new ObjectMapper();
            SimpleModule mod = new SimpleModule();
            mod.addDeserializer(SchemaProperty.class, new SchemaPropertyDeserializer(SchemaProperty.class));
            mod.addDeserializer(SchemaDataType.class, new SchemaDataTypeDeserializer(SchemaDataType.class));
            mod.addDeserializer(SchemaClass.class, new SchemaClassDeserializer(SchemaClass.class));
            mod.addDeserializer(SchemaEnumMember.class, new SchemaEnumMemberDeserializer(SchemaEnumMember.class));
            mod.addDeserializer(SchemaGraph.class, new SchemaGraphDeserializer(SchemaGraph.class));
            OBJECT_MAPPER_INSTNACE.registerModule(mod);
        }
        return OBJECT_MAPPER_INSTNACE;
    }



    static String getLabel(JsonNode node) {
        if (node.has(LABEL)) {
            return node.get(LABEL).asText();
        }
        return null;
    }

    static String getComment(JsonNode node) {
        if (node.has(COMMENT)) {
            return node.get(COMMENT).asText();
        }
        return null;
    }

    static Set<String> extractAsSingleFieldOrArray(JsonNode node, String fieldName) {
        if (!node.has(fieldName)) {
            return Collections.emptySet();
        }

        JsonNode fieldNode = node.get(fieldName);

        if (fieldNode.isArray()) {
            return extractArrayAsSet(fieldNode);
        }

        if (fieldNode.isObject()) {
            return extractObjectAsSet(fieldNode);
        }

        return Collections.emptySet();
    }

    private static Set<String> extractObjectAsSet(JsonNode fieldNode) {
        return Collections.singleton(fieldNode.get(ID).asText());
    }

    private static Set<String> extractArrayAsSet(JsonNode node) {
        Set<String> result = new HashSet<>();
        for (int i = 0; i < node.size(); i++) {
            result.add(node.get(i).get(ID).asText());
        }
        return result;
    }

    public static FieldSpec generateNextField(String packageName, String className) {
        return FieldSpec.builder(ClassName.get(packageName, className), "next" + className, Modifier.PRIVATE).build();
    }

}
