package com.schemaorg4j.parser.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.schemaorg4j.domain.Book;
import com.schemaorg4j.domain.Person;
import com.schemaorg4j.domain.combo.DateOrDateTime;
import com.schemaorg4j.parser.core.SchemaMapper;
import com.schemaorg4j.parser.core.domain.DataValueObject;
import com.schemaorg4j.parser.core.domain.DataValueString;
import com.schemaorg4j.parser.core.mappers.MappingArguments;
import com.schemaorg4j.parser.core.mappers.SimpleObjectMapper;
import com.schemaorg4j.util.OrText;
import org.junit.Test;

public class SimpleObjectTypeMapperTest {

    @Test
    public void canHandleValueObjects() {
        assertTrue(new SimpleObjectMapper(new SchemaMapper())
            .canHandle(Book.class, new DataValueObject("Book")));
    }

    @Test
    public void wontHandleComplexComboTypes() {
        assertFalse(new SimpleObjectMapper(new SchemaMapper())
            .canHandle(DateOrDateTime.class, new DataValueObject("Book")));
    }

    @Test
    public void wontHandleParameterizedOrTypes() {
        assertFalse(new SimpleObjectMapper(new SchemaMapper())
            .canHandle(OrText.class, new DataValueObject("Book")));
    }

    @Test
    public void canMapASimpleObjectWithOneLayerOfProperties() {
        DataValueObject unparsedBook = new DataValueObject("Book");
        unparsedBook.putField("isbn", new DataValueString("01"));
        unparsedBook.putField("numberOfPages", new DataValueString("7"));

        Book book = (Book) new SimpleObjectMapper(new SchemaMapper()).handle(new MappingArguments(unparsedBook));
        assertEquals(book.getIsbn(), "01");
        assertEquals(book.getNumberOfPages().intValue(), 7);
    }

    @Test
    public void canMapASimpleObjectWithTwoLayersOfProperties() {
        DataValueObject unparsedPerson  = new DataValueObject("Person");
        unparsedPerson.putField("additionalName", new DataValueString("Me"));

        DataValueObject unparsedBook = new DataValueObject("Book");
        unparsedBook.putField("isbn", new DataValueString("01"));
        unparsedBook.putField("numberOfPages", new DataValueString("7"));
        unparsedBook.putField("illustrator", unparsedPerson);

        Book book = (Book) new SimpleObjectMapper(new SchemaMapper()).handle(new MappingArguments(unparsedBook));

        assertEquals(Book.Illustrator.andThen(Person.AdditionalName).get(book).getValue(), "Me");
    }
}
