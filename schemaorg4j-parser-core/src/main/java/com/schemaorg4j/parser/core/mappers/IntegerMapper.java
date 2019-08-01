package com.schemaorg4j.parser.core.mappers;

import com.schemaorg4j.parser.core.domain.DataValue;
import com.schemaorg4j.parser.core.domain.DataValueString;
import java.util.Objects;

public class IntegerMapper extends SpecificTypeSchemaMapper {

    @Override
    public boolean canHandle(Class<?> clazz, DataValue argument) {
        return Objects.equals(Integer.class, clazz) && argument instanceof DataValueString;
    }

    @Override
    public Object handle(MappingArguments mappingArguments) {
        return ((DataValueString) mappingArguments.getArgument()).asInteger();
    }
}
