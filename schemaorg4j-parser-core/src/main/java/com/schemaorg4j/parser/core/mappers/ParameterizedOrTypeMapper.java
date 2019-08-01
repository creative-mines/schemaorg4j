package com.schemaorg4j.parser.core.mappers;

import com.schemaorg4j.annotations.SchemaOrg4JOrType;
import com.schemaorg4j.domain.Thing;
import com.schemaorg4j.domain.ThingImpl;
import com.schemaorg4j.domain.error.SchemaOrg4JError;
import com.schemaorg4j.parser.core.SchemaMapper;
import com.schemaorg4j.parser.core.domain.DataValue;
import com.schemaorg4j.parser.core.domain.DataValueList;
import com.schemaorg4j.parser.core.domain.DataValueObject;
import com.schemaorg4j.parser.core.domain.DataValueString;
import com.schemaorg4j.util.OrText;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterizedOrTypeMapper extends SpecificTypeSchemaMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterizedOrTypeMapper.class);

    private final SchemaMapper mapper;
    private final BasicDataTypeMapper basicTypeMapper;

    public ParameterizedOrTypeMapper(SchemaMapper mapper) {
        this.basicTypeMapper = new BasicDataTypeMapper();
        this.mapper = mapper;
    }

    @Override
    public boolean canHandle(Class<?> clazz, DataValue argument) {
        return isParameterisedOrClass(clazz) && !(argument instanceof DataValueList);
    }

    @Override
    public Object handle(MappingArguments mappingArguments) {
        DataValue dataValue = mappingArguments.getArgument();

        Annotation annotation = mappingArguments.getParameterAnnotation();
        if (!(annotation instanceof SchemaOrg4JOrType)) {
            return asText(dataValue);
        }

        SchemaOrg4JOrType orTypeAnnotation = (SchemaOrg4JOrType) annotation;
        Class<?> dataValueClass = orTypeAnnotation.value();

        if (dataValue instanceof DataValueObject) {

            Thing mappedValue = mapper.map((DataValueObject) dataValue);

            if (orTypeAnnotation.value().isInstance(mappedValue)) {
                return OrText.value(mappedValue);
            } else {
                // This is a data value (not text) and it's not an instance of
                // the appropriate alternative class.  Return an error thing.
                return errorThing(String.format(
                    "Object was of wrong type for parameterized or.  Expected %s and got %s",
                    orTypeAnnotation.value().toString(), mappedValue.getClass().toString()),
                    mappedValue);
            }
        }

        try {
            // If data value class is not a primitive type, then we have a string
            Object dataValueInstance = dataValueClass.newInstance();
            Object underlyingValue = basicTypeMapper.asValue(dataValueClass, dataValue);
            Method setter = dataValueClass
                .getMethod("setValue", underlyingValue.getClass());
            setter.invoke(dataValueInstance, underlyingValue);
            return OrText.value(dataValueInstance);
        } catch (Exception e) {
            LOGGER.debug("Exception setting parameterized or type member", e);
            try {
                return asText(dataValue);
            } catch (Exception stringInterpretError) {
                LOGGER.debug("Exception interpreting {} as a string", dataValue);
                LOGGER.debug("Original error", stringInterpretError);
                return errorThing("Error interpreting data value as string", dataValue);
            }
        }
    }

    private Thing errorThing(String message, Object contents) {
        Thing errorThing = new ThingImpl();
        errorThing.setSchemaOrg4JErrors(new ArrayList<SchemaOrg4JError>() {{
            add(new SchemaOrg4JError(message, contents));
        }});
        return errorThing;
    }

    private OrText asText(DataValue dataValue) {
        return OrText.text(((DataValueString) dataValue).asString());
    }
}
