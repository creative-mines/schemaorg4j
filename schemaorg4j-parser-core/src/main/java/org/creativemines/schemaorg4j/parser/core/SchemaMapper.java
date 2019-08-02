package org.creativemines.schemaorg4j.parser.core;

import static org.creativemines.schemaorg4j.parser.core.MethodUtil.findSetter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.creativemines.schemaorg4j.SchemaOrg4JConstants;
import org.creativemines.schemaorg4j.domain.Thing;
import org.creativemines.schemaorg4j.domain.ThingImpl;
import org.creativemines.schemaorg4j.domain.error.SchemaOrg4JError;
import org.creativemines.schemaorg4j.parser.core.domain.DataValue;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueList;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueObject;
import org.creativemines.schemaorg4j.parser.core.domain.Field;
import org.creativemines.schemaorg4j.parser.core.mappers.AdditionalDataFactory;
import org.creativemines.schemaorg4j.parser.core.mappers.MappingArguments;
import org.creativemines.schemaorg4j.parser.core.mappers.SpecificTypeSchemaMapperChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaMapper.class);
    private final SpecificTypeSchemaMapperChain chain;

    public SchemaMapper() {
        this.chain = new SpecificTypeSchemaMapperChain(this);
    }

    public Thing map(DataValueObject dataValueObject) {

        AdditionalDataFactory additionalDataFactory = new AdditionalDataFactory();
        List<SchemaOrg4JError> errors = new ArrayList<>();

        try {
            Class<?> clazz = Class
                .forName(SchemaOrg4JConstants.DOMAIN_PACKAGE + "." + dataValueObject.getType() + "Impl");
            Object instance = clazz.newInstance();

            for (Field f : dataValueObject.getFields()) {

                DataValue argument = dataValueObject.getValueFor(f);
                Optional<Method> maybeMethod = findSetter(f.getName(), clazz);

                if (maybeMethod.isPresent() && mustUseDataSetter(
                    maybeMethod.get().getParameterTypes()[0], argument)) {
                    maybeMethod = MethodUtil.findDataSetter(f.getName(), clazz);
                }

                if (!maybeMethod.isPresent()) {
                    LOGGER.debug("Unable to resolve a setter for field {}, skipping", f.getName());
                    additionalDataFactory.track(f, argument);
                    continue;
                }

                Method methodObject = maybeMethod.get();

                Class<?> parameterType = methodObject.getParameterTypes()[0];

                MappingArguments mappingArguments = new MappingArguments(argument, parameterType);
                if (methodObject.getParameterAnnotations().length > 0
                    && methodObject.getParameterAnnotations()[0].length > 0) {
                    mappingArguments = new MappingArguments(argument, parameterType,
                        methodObject.getParameterAnnotations()[0][0]);
                }

                Optional<Object> maybeArgument = chain.handle(mappingArguments);
                if (maybeArgument.isPresent()) {
                    try {
                        methodObject.invoke(instance, maybeArgument.get());
                        continue;
                    } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                        // Ignore the error, all errors sink outside of this if statement
                    }
                }

                errors.add(new SchemaOrg4JError(
                    "Unable to resolve proper argument for setter " + methodObject.getName()
                        + ", skipping", dataValueObject));
            }

            Thing returnValue = (Thing) instance;
            returnValue.setSchemaOrg4JAdditionalData(additionalDataFactory.asJson());
            returnValue.setSchemaOrg4JErrors(errors);
            return returnValue;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            Thing t = new ThingImpl();
            t.setSchemaOrg4JErrors(new ArrayList<SchemaOrg4JError>() {{
                SchemaOrg4JError error = new SchemaOrg4JError(
                    "Could not instantiate class of type " + dataValueObject.getType() + ": " + e
                        .getMessage(), dataValueObject);
                add(error);
            }});
            return t;
        }
    }

    private boolean mustUseDataSetter(Class<?> parameterType, DataValue value) {
        return value instanceof DataValueList && (
            Objects.equals(String.class, parameterType) ||
                Objects.equals(Boolean.class, parameterType) ||
                Objects.equals(Float.class, parameterType) ||
                Objects.equals(Integer.class, parameterType) ||
                Objects.equals(LocalDate.class, parameterType) ||
                Objects.equals(ZonedDateTime.class, parameterType) ||
                Objects.equals(LocalTime.class, parameterType)
        );
    }

    public List<Thing> map(DataValueList dataValueList) {
        return dataValueList.getList().stream().map(object -> this.map((DataValueObject) object))
            .collect(Collectors.toList());
    }

    public <T> T map(DataValueObject dataValueObject, Class<T> clazz) {
        return clazz.cast(map(dataValueObject));
    }

    public <T> List<T> map(DataValueList dataValueList, Class<T> clazz) {
        return dataValueList.getList().stream()
            .map(object -> clazz.cast(this.map((DataValueObject) object)))
            .collect(Collectors.toList());
    }
}
