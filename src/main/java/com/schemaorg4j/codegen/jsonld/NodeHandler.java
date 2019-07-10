package com.schemaorg4j.codegen.jsonld;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public interface NodeHandler {

    boolean canHandle(JsonNode node);

    void handle(JsonNode node) throws IOException;
}
