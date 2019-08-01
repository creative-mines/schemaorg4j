package com.schemaorg4j.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SchemaOrg4JComboClass {
    /**
     * Possible types that this combo class can represent
     * @return
     */
    Class<?>[] value() default {};
}
