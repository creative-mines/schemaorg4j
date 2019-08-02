package org.creativemines.schemaorg4j.codegen.jsonld;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.ID;
import static org.creativemines.schemaorg4j.codegen.constants.SchemaOrgConstants.SUBCLASS_OF;
import static org.creativemines.schemaorg4j.codegen.jsonld.Util.extractAsSingleFieldOrArray;
import static org.creativemines.schemaorg4j.codegen.jsonld.Util.getComment;
import static org.creativemines.schemaorg4j.codegen.jsonld.Util.getLabel;

import java.io.IOException;
import java.util.Set;

import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;
import org.creativemines.schemaorg4j.codegen.domain.SchemaClassBuilder;

public class SchemaClassDeserializer extends StdDeserializer<SchemaClass> {

    protected SchemaClassDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SchemaClass deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

        JsonNode node = p.getCodec().readTree(p);

        return new SchemaClassBuilder()
            .setId(node.get(ID).asText())
            .setLabel(getLabel(node))
            .setComment(getComment(node))
            .setSubclassOfIds(getSubclassIds(node))
            .createSchemaClass();
    }

    private Set<String> getSubclassIds(JsonNode node) {
        return extractAsSingleFieldOrArray(node, SUBCLASS_OF);
    }
}
