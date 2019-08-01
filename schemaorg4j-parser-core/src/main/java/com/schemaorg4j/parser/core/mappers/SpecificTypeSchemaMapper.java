package com.schemaorg4j.parser.core.mappers;

import com.schemaorg4j.annotations.SchemaOrg4JComboClass;
import com.schemaorg4j.parser.core.domain.DataValue;
import com.schemaorg4j.util.OrText;
import java.util.Objects;

public abstract class SpecificTypeSchemaMapper {

    public abstract boolean canHandle(Class<?> clazz, DataValue argument);

    public abstract Object handle(MappingArguments mappingArguments);

    public static boolean isComboType(Class<?> clazz) {
        return isParameterisedOrClass(clazz) || isComplexComboClass(clazz);
    }

    public static boolean isParameterisedOrClass(Class<?> clazz) {
        return Objects.equals(clazz, OrText.class);
    }

    public static boolean isComplexComboClass(Class<?> clazz) {
        return clazz.getAnnotationsByType(SchemaOrg4JComboClass.class).length > 0;
    }
}
