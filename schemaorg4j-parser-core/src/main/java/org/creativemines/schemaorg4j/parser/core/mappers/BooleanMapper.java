package org.creativemines.schemaorg4j.parser.core.mappers;

import java.util.Objects;

import org.creativemines.schemaorg4j.parser.core.domain.DataValue;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueString;

public class BooleanMapper extends SpecificTypeSchemaMapper {


    @Override
    public boolean canHandle(Class<?> clazz, DataValue argument) {
        return Objects.equals(Boolean.class, clazz) && argument instanceof DataValueString;
    }

    @Override
    public Object handle(MappingArguments mappingArguments) {
        return ((DataValueString) mappingArguments.getArgument()).asBoolean();
    }
}
