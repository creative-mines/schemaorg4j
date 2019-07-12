package com.schemaorg4j.codegen.factory.types;

import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.squareup.javapoet.TypeName;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTypeFactory implements TypeFactory {

    private final ArrayList<TypeHandler> chain;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTypeFactory.class);

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


        LOGGER.warn("Unable to determine type for field " + property.getId());
        return null;
    }
}
