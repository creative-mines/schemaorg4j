package org.creativemines.schemaorg4j.parser.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.leangen.geantyref.AnnotationFormatException;
import io.leangen.geantyref.TypeFactory;
import java.util.HashMap;

import org.creativemines.schemaorg4j.annotations.SchemaOrg4JOrType;
import org.creativemines.schemaorg4j.domain.Book;
import org.creativemines.schemaorg4j.domain.combo.DateOrDateTime;
import org.creativemines.schemaorg4j.parser.core.SchemaMapper;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueObject;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueString;
import org.creativemines.schemaorg4j.parser.core.mappers.MappingArguments;
import org.creativemines.schemaorg4j.parser.core.mappers.ParameterizedOrTypeMapper;
import org.creativemines.schemaorg4j.util.OrText;
import org.junit.Test;

public class ParameterizedOrTypeMapperTest {

    @Test
    public void canHandleOrTextTypes() {
        assertTrue(new ParameterizedOrTypeMapper(new SchemaMapper())
            .canHandle(OrText.class, new DataValueObject("OrText")));
    }

    @Test
    public void cantHandleComplexOrTypes() {
        assertFalse(new ParameterizedOrTypeMapper(new SchemaMapper())
            .canHandle(DateOrDateTime.class, new DataValueObject("DateOrDateTime")));
    }

    @Test
    public void parsesTextPartOfOrText() {
        OrText test = (OrText) new ParameterizedOrTypeMapper(new SchemaMapper())
            .handle(new MappingArguments(new DataValueString("test")));
        assertEquals(test.getText(), "test");
    }

    @Test
    public void parsesObjectPartOfOrText() throws AnnotationFormatException {
        DataValueObject unparsedBook = new DataValueObject("Book");
        unparsedBook.putField("isbn", new DataValueString("01"));

        OrText test = (OrText) new ParameterizedOrTypeMapper(new SchemaMapper()).handle(
            new MappingArguments(unparsedBook, OrText.class,
                TypeFactory.annotation(SchemaOrg4JOrType.class, new HashMap<String, Object>() {{
                    put("value", org.creativemines.schemaorg4j.domain.Book.class);
                }})));
        Book book = (Book) test.getValue();
        assertEquals(book.getIsbn(), "01");
    }

    @Test
    public void getsPrimitiveOutOfDataValueString() throws AnnotationFormatException {
        OrText<org.creativemines.schemaorg4j.domain.datatypes.Integer> test = (OrText) new ParameterizedOrTypeMapper(
            new SchemaMapper()).handle(new MappingArguments(new DataValueString("7"), OrText.class,
            TypeFactory.annotation(SchemaOrg4JOrType.class, new HashMap<String, Object>() {{
                put("value", org.creativemines.schemaorg4j.domain.datatypes.Integer.class);
            }})));
        assertEquals(test.getValue().getValue().intValue(), 7);
    }
}
