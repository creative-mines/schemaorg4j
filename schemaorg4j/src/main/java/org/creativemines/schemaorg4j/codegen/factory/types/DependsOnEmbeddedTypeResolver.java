package org.creativemines.schemaorg4j.codegen.factory.types;

import com.squareup.javapoet.TypeName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.domain.SchemaProperty;
import org.creativemines.schemaorg4j.codegen.domain.SchemaPropertyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DependsOnEmbeddedTypeResolver {

    private final List<TypeHandler> chain;

    private static final Logger LOGGER = LoggerFactory
        .getLogger(DependsOnEmbeddedTypeResolver.class);

    public DependsOnEmbeddedTypeResolver(SchemaGraph graph) {
        this.chain = new ArrayList<TypeHandler>() {{
            add(new SimpleTypeHandler());
            add(new ObjectTypeHandler(graph));
        }};
    }

    protected FieldDeclarationRequirement resolveEmbeddedType(String typeId) {
        // TODO: Should not have to wrap in a schema property just for this
        SchemaProperty property = new SchemaPropertyBuilder()
            .setRangeIncludesIds(Collections.singleton(typeId)).createSchemaProperty();
        Optional<TypeHandler> maybeHandler = this.chain.stream()
            .filter(handler -> handler.canHandle(property)).findFirst();

        if (maybeHandler.isPresent()) {
            return maybeHandler.get().handle(property);
        }

        LOGGER.warn("Unable to handle type " + typeId + " in embedded resolver, skipping");
        return null;
    }
}
