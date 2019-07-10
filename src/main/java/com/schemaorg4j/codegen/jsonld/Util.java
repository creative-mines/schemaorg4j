package com.schemaorg4j.codegen.jsonld;

import static com.schemaorg4j.codegen.jsonld.SchemaOrgConstants.COMMENT;
import static com.schemaorg4j.codegen.jsonld.SchemaOrgConstants.ID;
import static com.schemaorg4j.codegen.jsonld.SchemaOrgConstants.LABEL;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Package private Util class specifically for json deserializers
 */
class Util {


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

}
