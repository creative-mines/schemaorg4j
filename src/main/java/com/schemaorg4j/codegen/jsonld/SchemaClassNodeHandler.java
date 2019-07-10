package com.schemaorg4j.codegen.jsonld;

import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.CLASS_TYPE;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.TYPE;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import java.io.IOException;
import java.util.Objects;

public class SchemaClassNodeHandler implements NodeHandler {

    private final ObjectMapper objectMapperInstance;
    private final SchemaGraph graph;
    private final ObjectCodec codec;

    public SchemaClassNodeHandler(SchemaGraph g, ObjectCodec codec) {
        this.codec = codec;
        this.objectMapperInstance = Util.getObjectMapperInstnace();
        graph = g;
    }

    @Override
    public boolean canHandle(JsonNode node) {
        return node.has(TYPE) && Objects.equals(node.get(TYPE).asText(), CLASS_TYPE);
    }

    @Override
    public void handle(JsonNode node) throws IOException {
        JsonParser parser = node.traverse();
        parser.setCodec(codec);
        graph.addClass(objectMapperInstance.readValue(parser, SchemaClass.class));
    }
}
