package com.schemaorg4j.factory.types;

import com.schemaorg4j.codegen.domain.SchemaDataType;
import com.schemaorg4j.codegen.domain.SchemaProperty;
import com.schemaorg4j.codegen.domain.SchemaPropertyBuilder;
import java.util.HashSet;

public class TypeFactoryTest {

    protected SchemaProperty schemaProperty(SchemaDataType type) {
        return new SchemaPropertyBuilder().setRangeIncludesIds(new HashSet<String>() {{
            add(type.getId());
        }}).createSchemaProperty();
    }

}
