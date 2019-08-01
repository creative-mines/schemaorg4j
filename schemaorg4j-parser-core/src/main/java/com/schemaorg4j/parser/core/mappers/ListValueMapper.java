package com.schemaorg4j.parser.core.mappers;

import com.schemaorg4j.domain.Thing;
import com.schemaorg4j.domain.ThingImpl;
import com.schemaorg4j.domain.error.SchemaOrg4JError;
import com.schemaorg4j.parser.core.MethodUtil;
import com.schemaorg4j.parser.core.SchemaMapper;
import com.schemaorg4j.parser.core.domain.DataValue;
import com.schemaorg4j.parser.core.domain.DataValueList;
import com.schemaorg4j.parser.core.domain.DataValueObject;
import com.schemaorg4j.parser.core.domain.DataValueString;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListValueMapper extends SpecificTypeSchemaMapper {

    private final SimpleValueMapper simpleThingMapper;
    private final BasicDataTypeMapper basicDataTypeMapper;
    private final ComplexOrTypeMapper complexOrTypeMapper;
    private final ParameterizedOrTypeMapper parameterizedOrTypeMapper;
    private SchemaMapper mapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListValueMapper.class);

    public ListValueMapper(SchemaMapper mapper) {
        this.mapper = mapper;
        this.simpleThingMapper = new SimpleValueMapper();
        this.basicDataTypeMapper = new BasicDataTypeMapper();
        this.complexOrTypeMapper = new ComplexOrTypeMapper(mapper);
        this.parameterizedOrTypeMapper = new ParameterizedOrTypeMapper(mapper);
    }

    @Override
    public boolean canHandle(Class<?> clazz, DataValue argument) {
        return argument instanceof DataValueList;
    }

    @Override
    public Object handle(MappingArguments mappingArguments) {
        Class<?> parameterClass = mappingArguments.getParameterClass();
        DataValueList valueList = (DataValueList) mappingArguments.getArgument();

        List<Object> mappedValuesList = new ArrayList<>();
        List<SchemaOrg4JError> errors = new ArrayList<>();

        for (DataValue valueItem : valueList.getList()) {
            if (simpleThingMapper.canHandle(parameterClass, valueItem)) {
                try {
                    mappedValuesList.add(
                        simpleThingMapper.handle(new MappingArguments(valueItem, parameterClass)));
                } catch (Exception e) {
                    errors.add(new SchemaOrg4JError(
                        "Error generating simple thing from value " + valueItem.toString() + ": "
                            + e.getMessage(), valueItem));
                }
            } else if (complexOrTypeMapper.canHandle(parameterClass, valueItem)) {
                try {
                    mappedValuesList.add(complexOrTypeMapper.handle(
                        new MappingArguments(valueItem, parameterClass,
                            mappingArguments.getParameterAnnotation())));
                } catch (Exception e) {
                    errors.add(new SchemaOrg4JError(
                        "Error generating complex or type mapping for value" + valueItem.toString()
                            + ": " + e, valueItem));
                }
            } else if (parameterizedOrTypeMapper.canHandle(parameterClass, valueItem)) {
                try {
                    mappedValuesList.add(parameterizedOrTypeMapper.handle(
                        new MappingArguments(valueItem, parameterClass,
                            mappingArguments.getParameterAnnotation())));
                } catch (Exception e) {
                    errors.add(new SchemaOrg4JError(
                        "Error generating parameterized or type mapping for value" + valueItem
                            .toString()
                            + ": " + e, valueItem));
                }
            } else if (valueItem instanceof DataValueString) {
                try {
                    Object value = basicDataTypeMapper.asValue(parameterClass, valueItem);
                    Object wrapperInstance = parameterClass.newInstance();
                    Method setter = parameterClass.getMethod("setValue", value.getClass());
                    setter.invoke(wrapperInstance, value);
                    mappedValuesList.add(wrapperInstance);
                } catch (Exception e) {
                    errors.add(new SchemaOrg4JError(
                        "Error generating simple value from " + valueItem.toString() + ": " + e
                            .getMessage(), valueItem));
                }
            } else if (valueItem instanceof DataValueObject) {
                try {
                    mappedValuesList.add(mapper.map((DataValueObject) valueItem, parameterClass));
                } catch (Exception e) {
                    errors.add(new SchemaOrg4JError(
                        "Error generating object value from " + valueItem.toString() + ": " + e
                            .getMessage(), valueItem));
                }
            } else {
                errors.add(
                    new SchemaOrg4JError("Could not handle value item " + valueItem.toString(),
                        valueItem));
            }
        }

        for (int i = 0; i < mappedValuesList.size() - 1; i++) {
            Object current = mappedValuesList.get(i);
            Object next = mappedValuesList.get(i + 1);

            Optional<Method> maybeSetter = MethodUtil
                .findSetter("next" + parameterClass.getSimpleName(), parameterClass);

            maybeSetter.ifPresent(setter -> {
                try {
                    setter.invoke(current, next);
                } catch (Exception e) {
                    errors.add(new SchemaOrg4JError(
                        "Unable to link to next element " + next.toString() + ": " + e.getMessage(),
                        next));
                }
            });
        }

        if (!mappedValuesList.isEmpty()) {
            try {
                Object returnValue = mappedValuesList.get(0);
                returnValue.getClass().getMethod("setSchemaOrg4JErrors", List.class)
                    .invoke(returnValue, errors);
                return returnValue;
            } catch (Exception e) {
                // Ignore the error other than debug logging it, handling for all cases will be done below
                LOGGER.debug("Error while setting error object", e);
            }
        }

        Thing returnThing = new ThingImpl();
        returnThing.setSchemaOrg4JErrors(errors);
        return returnThing;
    }
}
