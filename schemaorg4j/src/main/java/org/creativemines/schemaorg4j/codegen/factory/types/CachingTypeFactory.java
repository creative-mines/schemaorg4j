package org.creativemines.schemaorg4j.codegen.factory.types;

import com.squareup.javapoet.TypeName;
import java.util.HashMap;
import java.util.Map;

import org.creativemines.schemaorg4j.codegen.domain.SchemaProperty;

public class CachingTypeFactory implements TypeFactory {

    private Map<String, FieldDeclarationRequirement> cache;
    private TypeFactory collaborator;

    public CachingTypeFactory(TypeFactory collaborator) {
        this.cache = new HashMap<>();
        this.collaborator = collaborator;
    }

    @Override
    public FieldDeclarationRequirement build(SchemaProperty property) {
        if (!cache.containsKey(property.getId())) {
            cache.put(property.getId(), collaborator.build(property));
        }
        return cache.get(property.getId());
    }
}
