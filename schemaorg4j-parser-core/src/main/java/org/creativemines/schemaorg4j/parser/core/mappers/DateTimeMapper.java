package org.creativemines.schemaorg4j.parser.core.mappers;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

import org.creativemines.schemaorg4j.parser.core.domain.DataValue;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeMapper extends SpecificTypeSchemaMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeMapper.class);

    @Override
    public boolean canHandle(Class<?> clazz, DataValue argument) {
        return Objects.equals(clazz, ZonedDateTime.class) && argument instanceof DataValueString;
    }

    @Override
    public Object handle(MappingArguments mappingArguments) {
        return ((DataValueString) mappingArguments.getArgument()).asDateTime();
    }

}
