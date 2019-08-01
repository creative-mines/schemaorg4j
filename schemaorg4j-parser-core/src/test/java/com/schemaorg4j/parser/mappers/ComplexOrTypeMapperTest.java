package com.schemaorg4j.parser.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.schemaorg4j.domain.combo.BooleanOrTextOrURL;
import com.schemaorg4j.domain.combo.DateOrDateTimeOrTime;
import com.schemaorg4j.domain.combo.IntegerOrQuantitativeValue;
import com.schemaorg4j.domain.combo.MonetaryAmountOrNumber;
import com.schemaorg4j.parser.core.SchemaMapper;
import com.schemaorg4j.parser.core.domain.DataValueObject;
import com.schemaorg4j.parser.core.domain.DataValueString;
import com.schemaorg4j.parser.core.mappers.ComplexOrTypeMapper;
import com.schemaorg4j.parser.core.mappers.MappingArguments;
import io.leangen.geantyref.AnnotationFormatException;
import org.junit.Test;

public class ComplexOrTypeMapperTest {

    @Test
    public void canHandleComplexObjectTypes() {
        assertTrue(new ComplexOrTypeMapper(new SchemaMapper())
            .canHandle(MonetaryAmountOrNumber.class,
                new DataValueObject("MonetaryAmount")));
    }

    @Test
    public void canMapObjectOrSimpleTypesWhenValueIsSimpleType() throws AnnotationFormatException {
        MonetaryAmountOrNumber morn = (MonetaryAmountOrNumber) new ComplexOrTypeMapper(
            new SchemaMapper())
            .handle(new MappingArguments(new DataValueString("100"), MonetaryAmountOrNumber.class));

        assertEquals(morn.getNumber(), 100.0, 0.1);
    }

    @Test
    public void canMapObjectOrSimpleTypesWhenValueIsObject() throws AnnotationFormatException {

        DataValueObject unparsedMonetaryAmount = new DataValueObject("MonetaryAmount");
        unparsedMonetaryAmount.putField("currency", new DataValueString("USD"));

        MonetaryAmountOrNumber morn = (MonetaryAmountOrNumber) new ComplexOrTypeMapper(
            new SchemaMapper())
            .handle(new MappingArguments(unparsedMonetaryAmount, MonetaryAmountOrNumber.class));

        assertEquals(morn.getMonetaryAmount().getCurrency(), "USD");
    }

    @Test
    public void canMapOrOfSimpleTypesText() {
        DataValueString unparsedString = new DataValueString("test");

        BooleanOrTextOrURL someObject = (BooleanOrTextOrURL) new ComplexOrTypeMapper(
            new SchemaMapper())
            .handle(new MappingArguments(unparsedString, BooleanOrTextOrURL.class));

        assertEquals(someObject.getText(), "test");
    }

    @Test
    public void canMapOrOfSimpleTypesBoolean() {
        DataValueString unparsedString = new DataValueString("true");

        BooleanOrTextOrURL someObject = (BooleanOrTextOrURL) new ComplexOrTypeMapper(
            new SchemaMapper())
            .handle(new MappingArguments(unparsedString, BooleanOrTextOrURL.class));

        assertEquals(someObject.get$Boolean(), true);
    }

    @Test
    public void canMapOrOfSimpleTypesUrl() {
        DataValueString unparsedString = new DataValueString("https://google.com");

        BooleanOrTextOrURL someObject = (BooleanOrTextOrURL) new ComplexOrTypeMapper(
            new SchemaMapper())
            .handle(new MappingArguments(unparsedString, BooleanOrTextOrURL.class));

        assertEquals(someObject.getURL(), "https://google.com");
    }

    @Test
    public void canMapOrOfDate() {
        DataValueString unparsedDate = new DataValueString("2006-10-01");

        DateOrDateTimeOrTime someTimeObject = (DateOrDateTimeOrTime) new ComplexOrTypeMapper(
            new SchemaMapper())
            .handle(new MappingArguments(unparsedDate, DateOrDateTimeOrTime.class));

        assertEquals(someTimeObject.getDate().getMonth().getValue(), 10);
    }

    @Test
    public void canMapOrOfDateTime() {
        DataValueString unparsedDate = new DataValueString("2006-10-01T20:00");

        DateOrDateTimeOrTime someTimeObject = (DateOrDateTimeOrTime) new ComplexOrTypeMapper(
            new SchemaMapper())
            .handle(new MappingArguments(unparsedDate, DateOrDateTimeOrTime.class));

        assertEquals(someTimeObject.getDateTime().getHour(), 20);
    }

    @Test
    public void canMapOrOfTime() {
        DataValueString unparsedDate = new DataValueString("20:00");

        DateOrDateTimeOrTime someTimeObject = (DateOrDateTimeOrTime) new ComplexOrTypeMapper(
            new SchemaMapper())
            .handle(new MappingArguments(unparsedDate, DateOrDateTimeOrTime.class));

        assertEquals(someTimeObject.getTime().getHour(), 20);
    }

    @Test
    public void canMapOrOfInteger() {
        DataValueString unparsedInteger = new DataValueString("7");

        IntegerOrQuantitativeValue someObject = (IntegerOrQuantitativeValue) new ComplexOrTypeMapper(
            new SchemaMapper())
            .handle(new MappingArguments(unparsedInteger, IntegerOrQuantitativeValue.class));

        assertEquals(someObject.getInteger().intValue(), 7);
    }
}
