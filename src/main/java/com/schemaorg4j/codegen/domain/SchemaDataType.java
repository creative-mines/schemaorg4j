package com.schemaorg4j.codegen.domain;

import static com.schemaorg4j.codegen.constants.Schema4JConstants.DATATYPES_PACKAGE;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.BOOLEAN_ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.DATETIME_ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.DATE_ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.FLOAT_ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.INTEGER_ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.NUMBER_ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.TEXT_ID;
import static com.schemaorg4j.codegen.constants.SchemaOrgConstants.TIME_ID;

public class SchemaDataType {

    private final String id;
    private final String javaDataType;

    public static final SchemaDataType BOOLEAN = new SchemaDataType(BOOLEAN_ID, "Boolean");
    public static final SchemaDataType DATE = new SchemaDataType(DATE_ID, DATATYPES_PACKAGE + ".Date");
    public static final SchemaDataType DATETIME = new SchemaDataType(DATETIME_ID, DATATYPES_PACKAGE + ".DateTime");
    public static final SchemaDataType NUMBER = new SchemaDataType(NUMBER_ID, "Float");
    public static final SchemaDataType FLOAT = new SchemaDataType(FLOAT_ID, "Float");
    public static final SchemaDataType INTEGER = new SchemaDataType(INTEGER_ID, "Integer");
    public static final SchemaDataType TEXT = new SchemaDataType(TEXT_ID, "String");
    public static final SchemaDataType TIME = new SchemaDataType(TIME_ID, DATATYPES_PACKAGE + ".Time");

    private SchemaDataType(String id, String javaDataType) {
        this.id = id;
        this.javaDataType = javaDataType;
    }

    public String getId() {
        return id;
    }

    public String getJavaDataType() {
        return javaDataType;
    }
}
