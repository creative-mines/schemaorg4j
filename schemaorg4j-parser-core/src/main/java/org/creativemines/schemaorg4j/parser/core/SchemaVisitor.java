package org.creativemines.schemaorg4j.parser.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.creativemines.schemaorg4j.annotations.SchemaOrg4JComboClass;
import org.creativemines.schemaorg4j.domain.Thing;
import org.creativemines.schemaorg4j.util.OrText;

public class SchemaVisitor {

    private final Consumer<Object> consumer;
    private final Thing head;

    public SchemaVisitor(Thing head, Consumer<Object> consumer) {
        this.head = head;
        this.consumer = consumer;
    }

    public void visit() {
        Stack<Object> objectsToVisit = new Stack<>();
        objectsToVisit.add(head);

        while (!objectsToVisit.isEmpty()) {
            Object currentObject = objectsToVisit.pop();
            consumer.accept(currentObject);

            List<Method> publicGetters = getPublicGetters(currentObject.getClass());
            publicGetters.forEach(method -> {
                Object result = null;
                try {
                    result = method.invoke(currentObject);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                if (result != null && (
                        result instanceof Thing ||
                        result.getClass().getAnnotationsByType(SchemaOrg4JComboClass.class).length > 0 ||
                        result.getClass().isAssignableFrom(OrText.class))
                ) {
                    objectsToVisit.push(result);
                }
            });
        }
    }

    private List<Method> getPublicGetters(Class<?> aClass) {
        return Arrays.stream(aClass.getMethods()).filter(method ->
            method.getName().startsWith("get")).collect(
            Collectors.toList());
    }
}
