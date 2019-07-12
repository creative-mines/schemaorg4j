package com.schemaorg4j.codegen.factory.types;

import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.DATATYPES_PACKAGE;

import com.schemaorg4j.codegen.domain.SchemaDataType;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

public class SimpleTypeHandler implements TypeHandler {

    private final HashMap<String, TypeName> ids;

    public SimpleTypeHandler() {
        ids = new HashMap<String, TypeName>() {{
            put(SchemaDataType.BOOLEAN.getId(), TypeName.BOOLEAN.box());
            put(SchemaDataType.NUMBER.getId(), TypeName.FLOAT.box());
            put(SchemaDataType.FLOAT.getId(), TypeName.FLOAT.box());
            put(SchemaDataType.INTEGER.getId(), TypeName.INT.box());
            put(SchemaDataType.TEXT.getId(), ClassName.get("java.lang", "String"));
            put(SchemaDataType.DATE.getId(), ClassName.get(DATATYPES_PACKAGE, "Date"));
            put(SchemaDataType.DATETIME.getId(), ClassName.get(DATATYPES_PACKAGE, "DateTime"));
            put(SchemaDataType.TIME.getId(), ClassName.get(DATATYPES_PACKAGE, "Time"));
            put(SchemaDataType.URL.getId(), ClassName.get("java.lang", "String"));
        }};
    }

    @Override
    public boolean canHandle(SchemaProperty property) {
        Set<String> fieldRange = property.getRangeIncludesIds();
        Optional<String> first = getFirstType(property);

        return fieldRange.size() == 1 && first.isPresent() && ids.containsKey(first.get());
    }

    @Override
    public TypeName handle(SchemaProperty property) {
        Optional<String> first = getFirstType(property);
        if (first.isPresent()) {
            return ids.get(first.get());
        }

        throw new UnsupportedOperationException("Should not have handled property " + property.getId());
    }

    private Optional<String> getFirstType(SchemaProperty property) {
        Set<String> fieldRange = property.getRangeIncludesIds();
        return fieldRange.stream().findFirst();
    }
}
