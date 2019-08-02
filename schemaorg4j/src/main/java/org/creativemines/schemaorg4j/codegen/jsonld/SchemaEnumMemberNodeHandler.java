package org.creativemines.schemaorg4j.codegen.jsonld;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.CLASS_TYPE;
import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.PROPERTY_TYPE;
import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.TYPE;

import java.io.IOException;
import java.util.Objects;

import org.creativemines.schemaorg4j.codegen.domain.SchemaEnumMember;
import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;

public class SchemaEnumMemberNodeHandler implements NodeHandler {

    private final ObjectMapper objectMapperInstance;
    private final SchemaGraph graph;
    private final ObjectCodec codec;

    public SchemaEnumMemberNodeHandler(SchemaGraph g, ObjectCodec codec) {
        this.graph = g;
        this.codec = codec;
        this.objectMapperInstance = Util.getObjectMapperInstnace();
    }

    @Override
    public boolean canHandle(JsonNode node) {
        return node.has(TYPE) && !Objects.equals(node.get(TYPE).asText(), CLASS_TYPE) && !Objects
            .equals(node.get(TYPE).asText(), PROPERTY_TYPE);
    }

    @Override
    public void handle(JsonNode node) throws IOException {
        JsonParser parser = node.traverse();
        parser.setCodec(codec);
        graph.addEnumMember(objectMapperInstance.readValue(parser, SchemaEnumMember.class));
    }
}
