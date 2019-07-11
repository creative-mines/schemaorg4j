package com.schemaorg4j.codegen.factory.types;

import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.schemaorg4j.codegen.domain.SchemaPropertyBuilder;
import com.squareup.javapoet.TypeName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DependsOnEmbeddedTypeResolver {

    private final List<TypeHandler> chain;

    public DependsOnEmbeddedTypeResolver(SchemaGraph graph) {
        this.chain = new ArrayList<TypeHandler>() {{
            add(new SimpleTypeHandler());
            add(new ObjectTypeHandler(graph));
        }};
    }

    protected TypeName resolveEmbeddedType(String typeId) {
        // TODO: Should not have to wrap in a schema property just for this
        SchemaProperty property = new SchemaPropertyBuilder()
            .setRangeIncludesIds(Collections.singleton(typeId)).createSchemaProperty();
        Optional<TypeHandler> maybeHandler = this.chain.stream()
            .filter(handler -> handler.canHandle(property)).findFirst();

        if (maybeHandler.isPresent()) {
            return maybeHandler.get().handle(property);
        }

        throw new UnsupportedOperationException("Unable to handle type " + typeId + " in embedded resolver");
    }
}
