package com.schemaorg4j.codegen.factory.types;

import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.squareup.javapoet.TypeName;
import java.util.ArrayList;

public class SimpleTypeFactory implements TypeFactory {

    private final ArrayList<TypeHandler> chain;

    public SimpleTypeFactory(SchemaGraph graph) {
        this.chain = new ArrayList<TypeHandler>() {{
            add(new SimpleTypeHandler());
            add(new ObjectTypeHandler(graph));
            add(new OrTextTypeHandler(graph));
            add(new MultiTypeHandler(graph));
        }};
    }

    public TypeName build(SchemaProperty property) {
        for (TypeHandler handler : chain) {
            if (handler.canHandle(property)) {
                return handler.handle(property);
            }
        }

        throw new UnsupportedOperationException("No handler was able to handle " + property.getId());
    }
}
