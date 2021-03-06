package org.creativemines.schemaorg4j.parser.core.mappers;

import java.lang.annotation.Annotation;

import org.creativemines.schemaorg4j.parser.core.domain.DataValue;

public class MappingArguments {

    private final DataValue argument;
    private final Class<?> parameterClass;
    private final Annotation parameterAnnotation;

    public MappingArguments(DataValue argument) {
        this.argument = argument;
        this.parameterClass = null;
        this.parameterAnnotation = null;
    }

    public MappingArguments(DataValue argument, Class<?> parameterClass) {
        this.argument = argument;
        this.parameterClass = parameterClass;
        this.parameterAnnotation = null;
    }

    public MappingArguments(DataValue argument, Class<?> parameterClass, Annotation parameterAnnotation) {
        this.argument = argument;
        this.parameterClass = parameterClass;
        this.parameterAnnotation = parameterAnnotation;
    }

    public DataValue getArgument() {
        return argument;
    }

    public Class<?> getParameterClass() {
        return parameterClass;
    }

    public Annotation getParameterAnnotation() {
        return parameterAnnotation;
    }
}
