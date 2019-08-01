package com.schemaorg4j.parser.core.mappers;

import com.schemaorg4j.annotations.SchemaOrg4JComboClass;
import com.schemaorg4j.domain.Thing;
import com.schemaorg4j.domain.datatypes.DataType;
import com.schemaorg4j.parser.core.domain.DataValue;
import com.schemaorg4j.parser.core.domain.DataValueString;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleValueMapper extends SpecificTypeSchemaMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleValueMapper.class);

    @Override
    public boolean canHandle(Class<?> clazz, DataValue argument) {
        return isAnObjectClass(clazz) && comboTypeDoesNotIncludeSimpleDataType(clazz) && argument instanceof DataValueString;
    }

    private boolean comboTypeDoesNotIncludeSimpleDataType(Class<?> clazz) {
        if (!isComplexComboClass(clazz)) {
            return true;
        }

        // If any of these classes could be parsed from a string, it will be handled in the multi
        // type handler
        Class<?>[] classValues = clazz.getAnnotationsByType(SchemaOrg4JComboClass.class)[0].value();
        return Arrays.stream(classValues).noneMatch(DataType.class::isAssignableFrom);
    }

    private boolean isAnObjectClass(Class<?> clazz) {
        return (Thing.class.isAssignableFrom(clazz) || isComplexComboClass(clazz));
    }

    @Override
    public Object handle(MappingArguments mappingArguments) {
        try {
            Class<?> parameterClass = mappingArguments.getParameterClass();
            Class<?> implementationClass = getImplementationClass(parameterClass);
            Object instance = implementationClass.newInstance();
            Method method = parameterClass.getMethod("setSimpleValue", String.class);
            method.invoke(instance, ((DataValueString) mappingArguments.getArgument()).asString());
            return instance;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            // TODO: Return an error
            LOGGER.error("Unable to instantiate object for use in simple value", e);
            return null;
        }
    }

    private Class<?> getImplementationClass(Class<?> parameterClass) throws ClassNotFoundException {
        if (Thing.class.isAssignableFrom(parameterClass)) {
            return Class.forName(parameterClass.getName() + "Impl");
        }
        return parameterClass;
    }
}
