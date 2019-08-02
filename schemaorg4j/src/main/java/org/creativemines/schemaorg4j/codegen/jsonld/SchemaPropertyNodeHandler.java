package org.creativemines.schemaorg4j.codegen.jsonld;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.PROPERTY_TYPE;
import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.TYPE;

import java.io.IOException;
import java.util.Objects;

import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.domain.SchemaProperty;

public class SchemaPropertyNodeHandler implements NodeHandler {

    private final SchemaGraph graph;
    private final ObjectCodec codec;
    private final ObjectMapper objectMapperInstance;

    public SchemaPropertyNodeHandler(SchemaGraph g, ObjectCodec c) {
        this.graph = g;
        this.codec = c;
        this.objectMapperInstance = Util.getObjectMapperInstnace();
    }

    @Override
    public boolean canHandle(JsonNode node) {
        return node.has(TYPE) && Objects.equals(node.get(TYPE).asText(), PROPERTY_TYPE);
    }

    @Override
    public void handle(JsonNode node) throws IOException {
        JsonParser p = node.traverse();
        p.setCodec(codec);
        graph.addProperty(objectMapperInstance.readValue(p, SchemaProperty.class));
    }
}
