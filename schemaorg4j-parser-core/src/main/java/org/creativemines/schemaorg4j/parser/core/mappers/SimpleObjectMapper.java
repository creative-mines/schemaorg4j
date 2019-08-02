package org.creativemines.schemaorg4j.parser.core.mappers;

import org.creativemines.schemaorg4j.parser.core.SchemaMapper;
import org.creativemines.schemaorg4j.parser.core.domain.DataValue;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueObject;

public class SimpleObjectMapper extends SpecificTypeSchemaMapper {

    private final SchemaMapper mapper;

    public SimpleObjectMapper(SchemaMapper mapper) {
        this.mapper =  mapper;
    }

    @Override
    public boolean canHandle(Class<?> clazz, DataValue argument) {
        return !isComboType(clazz) && argument instanceof DataValueObject;
    }

    @Override
    public Object handle(MappingArguments mappingArguments) {
        return mapper.map((DataValueObject) mappingArguments.getArgument());
    }
}
