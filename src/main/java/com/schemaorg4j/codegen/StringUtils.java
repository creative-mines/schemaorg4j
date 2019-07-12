package com.schemaorg4j.codegen;

public class StringUtils {
    public static String orLabelFromId(String label, String id) {
        if (label == null || label.isEmpty()) {
            return id.substring(id.lastIndexOf("/") + 1);
        }
        return label;
    }

    public static String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static String decapitalize(String name) {
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }
}
