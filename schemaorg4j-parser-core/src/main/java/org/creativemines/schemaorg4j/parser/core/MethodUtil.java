package org.creativemines.schemaorg4j.parser.core;

import static org.creativemines.schemaorg4j.parser.core.StringUtil.capitalize;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

public class MethodUtil {

    public static <T> Optional<Method> findSetter(String fieldName, Class<T> clazz) {
        return findSetterByTarget("set" + capitalize(fieldName), clazz);
    }

    public static Optional<Method> findDataSetter(String name, Class<?> clazz) {
        return findSetterByTarget("set" + capitalize(name) + "Data", clazz);
    }

    private static Optional<Method> findSetterByTarget(String targetName, Class<?> clazz) {
        for (Method m : clazz.getDeclaredMethods()) {
            if (Objects.equals(m.getName(), targetName)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();

    }
}
