package org.creativemines.schemaorg4j.codegen;

public class StringUtils {
    public static String orLabelFromId(String label, String id) {
        if (label == null || label.isEmpty()) {
            return id.substring(id.lastIndexOf("/") + 1);
        }
        return label;
    }

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

    public static String escapeDollar(String comment) {
        if (comment == null) {
            return "";
        }
        return comment.replace("$", "$$");
    }
}
