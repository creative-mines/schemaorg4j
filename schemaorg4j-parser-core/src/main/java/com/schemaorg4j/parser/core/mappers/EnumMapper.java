package com.schemaorg4j.parser.core.mappers;

import com.schemaorg4j.parser.core.domain.DataValue;
import com.schemaorg4j.parser.core.domain.DataValueString;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumMapper extends SpecificTypeSchemaMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnumMapper.class);

    @Override
    public boolean canHandle(Class<?> clazz, DataValue argument) {
        try {
            return clazz.getMethod("getEnumMembers") != null && argument instanceof DataValueString;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    @Override
    public Object handle(MappingArguments mappingArguments) {
        Class<?> clazz = mappingArguments.getParameterClass();
        try {
            Method getter = clazz.getMethod("getEnumMembers");
            Class<?> enumType = getter.getReturnType();
            Method setter = clazz.getMethod("setEnumMembers", enumType);

            String enumConstantName = ((DataValueString) mappingArguments.getArgument()).asString();
            Optional<Object> maybeEnumMember = findClosestMember(enumType.getEnumConstants(),
                enumConstantName);

            if (!maybeEnumMember.isPresent()) {
                LOGGER.debug("Unable to find enum member in class {} to match {}",
                    enumType.getSimpleName(), enumConstantName);
                return null;
            }

            try {
                Object enumClassInstance = Class.forName(clazz.getName() + "Impl").newInstance();
                setter.invoke(enumClassInstance, maybeEnumMember.get());
                return enumClassInstance;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
                LOGGER.debug("Could not instantiate enum member {}", maybeEnumMember);
                LOGGER.debug("Original error", e);
            }

        } catch (NoSuchMethodException e) {
            LOGGER.error("Handle called without canHandle", e);
        }

        return null;
    }

    private Optional<Object> findClosestMember(Object[] enumConstants, String unparsedText) {
        return Arrays.stream(enumConstants).filter(
            constant -> unparsedText.toLowerCase().contains(constant.toString().toLowerCase()))
            .findFirst();
    }
}
