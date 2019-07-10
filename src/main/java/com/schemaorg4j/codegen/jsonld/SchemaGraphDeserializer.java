package com.schemaorg4j.codegen.jsonld;

import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.GRAPH;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.schemaorg4j.codegen.domain.SchemaGraph;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaGraphDeserializer extends StdDeserializer<SchemaGraph> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaGraph.class);

    protected SchemaGraphDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SchemaGraph deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        SchemaGraph graph = new SchemaGraph();
        List<NodeHandler> chain = getChain(graph, p.getCodec());


        JsonNode graphNode = node.get(GRAPH);
        for (int nodeIndex = 0; nodeIndex < graphNode.size(); nodeIndex++) {
            handleChain(chain, graphNode.get(nodeIndex));
        }

        return graph;
    }

    private void handleChain(List<NodeHandler> chain, JsonNode node) throws IOException {
        for (NodeHandler handler : chain) {
            if (handler.canHandle(node)) {
                handler.handle(node);
                return;
            }
        }
        LOGGER.warn("Unable to handle node, skipping: {}", node.toString());
    }

    public List<NodeHandler> getChain(SchemaGraph g, ObjectCodec codec) {
        return new ArrayList<NodeHandler>() {{
            add(new SchemaClassNodeHandler(g, codec));
        }};
    }
}
