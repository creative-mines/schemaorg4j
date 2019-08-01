package com.schemaorg4j.parser.core.mappers;

import com.schemaorg4j.parser.core.domain.DataValue;
import com.schemaorg4j.parser.core.domain.DataValueString;
import java.util.Objects;

public class FloatMapper extends SpecificTypeSchemaMapper {

    @Override
    public boolean canHandle(Class<?> clazz, DataValue argument) {
        return Objects.equals(Float.class, clazz) && argument instanceof DataValueString;
    }

    @Override
    public Object handle(MappingArguments arguments) {
        return ((DataValueString) arguments.getArgument()).asFloat();
    }
}
