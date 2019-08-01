package com.schemaorg4j.parser.core;

import com.schemaorg4j.parser.core.domain.DataValue;
import com.schemaorg4j.parser.core.domain.DataValueList;
import com.schemaorg4j.parser.core.domain.DataValueObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;

public class SchemaHandler {

    private final SchemaMapper schemaMapper;
    private final HashMap<Class, Collection<Consumer>> handlers;

    public SchemaHandler(SchemaMapper schemaMapper) {
        this.handlers = new HashMap<>();
        this.schemaMapper = schemaMapper;
    }

    public <T> SchemaHandler addHandler(Class<T> clazz, Consumer<T> c) {
        if (handlers.containsKey(clazz)) {
            handlers.get(clazz).add(c);
        } else {
            handlers.put(clazz, new ArrayList<Consumer>() {{
                add(c);
            }});
        }

        return this;
    }

    public void consume(DataValueObject dataValueObject) {
        new SchemaVisitor(schemaMapper.map(dataValueObject), this::handle).visit();
    }

    public void consume(DataValueList dataValueList) {
        schemaMapper.map(dataValueList).forEach(thing -> {
            new SchemaVisitor(thing, this::handle).visit();
        });
    }

    private void handle(Object node) {
        handlers.keySet().stream()
            .filter(key -> key.isAssignableFrom(node.getClass()))
            .flatMap(key -> handlers.get(key).stream())
            .forEach(consumer -> consumer.accept(node));
    }

}
