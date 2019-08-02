package org.creativemines.schemaorg4j.parser.core.mappers;

import static org.creativemines.schemaorg4j.parser.core.StringUtil.decapitalize;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import org.creativemines.schemaorg4j.annotations.SchemaOrg4JComboClass;
import org.creativemines.schemaorg4j.domain.Thing;
import org.creativemines.schemaorg4j.domain.ThingImpl;
import org.creativemines.schemaorg4j.domain.error.SchemaOrg4JError;
import org.creativemines.schemaorg4j.parser.core.MethodUtil;
import org.creativemines.schemaorg4j.parser.core.SchemaMapper;
import org.creativemines.schemaorg4j.parser.core.domain.DataValue;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueList;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComplexOrTypeMapper extends SpecificTypeSchemaMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComplexOrTypeMapper.class);
    private final SchemaMapper mapper;
    private final BasicDataTypeMapper basicTypeMapper;

    public ComplexOrTypeMapper(SchemaMapper mapper) {
        this.basicTypeMapper = new BasicDataTypeMapper();
        this.mapper = mapper;
    }

    @Override
    public boolean canHandle(Class<?> clazz, DataValue argument) {
        return isComplexComboClass(clazz) && !(argument instanceof DataValueList);
    }

    @Override
    public Object handle(MappingArguments mappingArguments) {
        DataValue dataValue = mappingArguments.getArgument();
        Class<?> parameterType = mappingArguments.getParameterClass();

        if (dataValue instanceof DataValueObject) {
            Object mappedValue = mapper.map((DataValueObject) dataValue);

            Method setter = null;
            outerSetterSearch: for (Method maybeSetter : parameterType.getMethods()) {
                if (!maybeSetter.getName().startsWith("set")) {
                    continue;
                }
                for (Class acceptedParameterType : maybeSetter.getParameterTypes()) {
                    if (acceptedParameterType.isInstance(mappedValue)) {
                        setter =  maybeSetter;
                        break outerSetterSearch;
                    }
                }
            }

            if (setter != null) {
                try {
                    Object mappedInstance = parameterType.newInstance();
                    setter.invoke(mappedInstance, mappedValue);
                    return mappedInstance;
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    // Ignore error here, error object will be generated below
                }
            }

            Thing errorThing = new ThingImpl();
            errorThing.setSchemaOrg4JErrors(new ArrayList<SchemaOrg4JError>() {{
                add(new SchemaOrg4JError(
                    "Unable to invoke setter on instance with argument " + mappedValue
                        .toString(), mappedValue));
            }});
            return errorThing;
        }

        SchemaOrg4JComboClass comboClassAnnotation = parameterType
            .getAnnotation(SchemaOrg4JComboClass.class);
        for (Class<?> clazz : comboClassAnnotation.value()) {
            try {
                Object mappedValue = basicTypeMapper.asValue(clazz, dataValue);

                Class<?> interfaceType = mappedValue.getClass();

                Optional<Method> maybeSetter = MethodUtil
                    .findSetter(decapitalize(interfaceType.getSimpleName()), parameterType);

                if (!maybeSetter.isPresent() && Objects
                    .equals(mappedValue.getClass(), ZonedDateTime.class)) {
                    maybeSetter = MethodUtil.findSetter("dateTime", parameterType);
                }

                if (!maybeSetter.isPresent() && Objects
                    .equals(mappedValue.getClass(), LocalDate.class)) {
                    maybeSetter = MethodUtil.findSetter("date", parameterType);
                }

                if (!maybeSetter.isPresent() && Objects
                    .equals(mappedValue.getClass(), LocalTime.class)) {
                    maybeSetter = MethodUtil.findSetter("time", parameterType);
                }

                if (!maybeSetter.isPresent() && Objects
                    .equals(mappedValue.getClass(), Float.class)) {
                    maybeSetter = MethodUtil.findSetter("number", parameterType);
                }

                if (!maybeSetter.isPresent() && Objects
                    .equals(mappedValue.getClass(), String.class)) {
                    try {
                        new URL((String) mappedValue);
                        maybeSetter = MethodUtil.findSetter("uRL", parameterType);
                    } catch (MalformedURLException e) {
                        // This is not actually a url
                    }
                }

                if (!maybeSetter.isPresent() && Objects
                    .equals(mappedValue.getClass(), Boolean.class)) {
                    maybeSetter = MethodUtil.findSetter("$boolean", parameterType);
                }

                if (!maybeSetter.isPresent() && Objects
                    .equals(mappedValue.getClass(), String.class)) {
                    maybeSetter = MethodUtil.findSetter("text", parameterType);
                }

                if (maybeSetter.isPresent()) {
                    Method setter = maybeSetter.get();
                    try {
                        Object mappedInstance = parameterType.newInstance();
                        setter.invoke(mappedInstance, mappedValue);
                        return mappedInstance;
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        LOGGER.debug("Unable to invoke method", e);
                        // Skip error here, it will be handled below
                    }
                }
            } catch (Exception e) {
                // Ignore error, will be handled by returning error thing
                LOGGER.debug("Unable to map simple value type in complex type mapper", e);
            }

        }

        Thing errorThing = new ThingImpl();
        errorThing.setSchemaOrg4JErrors(new ArrayList<SchemaOrg4JError>() {{
            add(new SchemaOrg4JError(
                "Unable to map dataValue for complex or type" + dataValue
                    .toString(), dataValue));
        }});
        return errorThing;
    }
}
