package org.creativemines.schemaorg4j.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface SchemaOrg4JOrType {

    /**
     * Class repesenting the value type of this OrText entry
     * @return
     */
    Class<?> value();
}
