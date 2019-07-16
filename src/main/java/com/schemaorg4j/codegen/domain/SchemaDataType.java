package com.schemaorg4j.codegen.domain;

import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.DATATYPES_PACKAGE;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.BOOLEAN_ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.DATETIME_ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.DATE_ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.FLOAT_ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.INTEGER_ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.NUMBER_ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.TEXT_ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.TIME_ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.URL_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SchemaDataType {

    private final String id;
    private final String javaDataType;
    private final String label;

    public static final SchemaDataType BOOLEAN = new SchemaDataType(BOOLEAN_ID, "Boolean", "Boolean");
    public static final SchemaDataType DATE = new SchemaDataType(DATE_ID, "Date", "Date");
    public static final SchemaDataType DATETIME = new SchemaDataType(DATETIME_ID, "Date", "DateTime");
    public static final SchemaDataType NUMBER = new SchemaDataType(NUMBER_ID, "Float", "Number");
    public static final SchemaDataType FLOAT = new SchemaDataType(FLOAT_ID, "Float", "Float");
    public static final SchemaDataType INTEGER = new SchemaDataType(INTEGER_ID, "Integer", "Integer");
    public static final SchemaDataType TEXT = new SchemaDataType(TEXT_ID, "String", "Text");
    public static final SchemaDataType TIME = new SchemaDataType(TIME_ID, DATATYPES_PACKAGE + ".Time", "Time");
    public static final SchemaDataType URL = new SchemaDataType(URL_ID, "String", "URL");

    private static final List<SchemaDataType> LIST = new ArrayList<SchemaDataType>() {{
        add(BOOLEAN);
        add(DATE);
        add(DATETIME);
        add(NUMBER);
        add(FLOAT);
        add(INTEGER);
        add(TEXT);
        add(TIME);
        add(URL);
    }};

    public static Optional<SchemaDataType> findById(String id) {
        return LIST.stream().filter(type -> Objects.equals(type.getId(), id)).findFirst();
    }

    private SchemaDataType(String id, String javaDataType, String label) {
        this.id = id;
        this.javaDataType = javaDataType;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getJavaDataType() {
        return javaDataType;
    }

    public String getLabel() {
        return label;
    }
}
