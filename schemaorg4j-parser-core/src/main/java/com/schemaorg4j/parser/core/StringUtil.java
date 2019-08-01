package com.schemaorg4j.parser.core;

public class StringUtil {
    public static String capitalize(String name) {
        if (name.startsWith("$")) {
            return name.substring(0, 2).toUpperCase() + name.substring(2);
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static String decapitalize(String name) {
        if (name.startsWith("$")) {
            return name.substring(0, 2).toLowerCase() + name.substring(2);
        }
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }
}
