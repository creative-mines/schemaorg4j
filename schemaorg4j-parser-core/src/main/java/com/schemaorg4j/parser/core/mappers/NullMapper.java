package com.schemaorg4j.parser.core.mappers;

import com.schemaorg4j.parser.core.domain.DataValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NullMapper extends SpecificTypeSchemaMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(NullMapper.class);

    @Override
    public boolean canHandle(Class<?> clazz, DataValue argument) {
        return true;
    }

    @Override
    public Object handle(MappingArguments mappingArguments) {
        LOGGER.debug("Data value {} made it to null mapper", mappingArguments.getArgument());
        return null;
    }
}
