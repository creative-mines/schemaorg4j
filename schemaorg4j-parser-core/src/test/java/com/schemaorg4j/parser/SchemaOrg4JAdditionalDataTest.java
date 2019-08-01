package com.schemaorg4j.parser;

import static com.schemaorg4j.parser.TestUtil.fromJson;
import static com.schemaorg4j.util.Lens.as;
import static org.junit.Assert.assertEquals;

import com.schemaorg4j.domain.Book;
import com.schemaorg4j.domain.Offer;
import com.schemaorg4j.domain.Thing;
import com.schemaorg4j.domain.WebPage;
import com.schemaorg4j.parser.core.SchemaMapper;
import com.schemaorg4j.parser.core.domain.DataValueObject;
import com.schemaorg4j.parser.core.domain.DataValueString;
import java.io.IOException;
import org.junit.Test;

public class SchemaOrg4JAdditionalDataTest {

    @Test
    public void additionalFieldsThatShowUpInAnObjectWillShowUpInAdditionalData()
        throws IOException {

        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"name\": \"Shostakovich Leningrad\",\n"
            + "  \"x-some-field\": \"Some extension field\"\n"
            + "}");

        Thing thing = new SchemaMapper().map(dataValueObject);
        assertEquals(((DataValueString) ((DataValueObject) thing.getSchemaOrg4JAdditionalData().getAdditionalData())
            .getValueFor("x-some-field")).asString(), "Some extension field");
    }

    @Test
    public void additionalDataProvidesAWayToHandleAdditionalTypes() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"WebPage\",\n"
            + "  \"breadcrumb\": \"Books > Literature & Fiction > Classics\",\n"
            + "  \"mainEntity\":{\n"
            + "          \"@type\": \"Book\",\n"
            + "          \"author\": \"/author/jd_salinger.html\",\n"
            + "          \"bookFormat\": \"http://schema.org/Paperback\",\n"
            + "          \"datePublished\": \"1991-05-01\",\n"
            + "          \"image\": \"catcher-in-the-rye-book-cover.jpg\",\n"
            + "          \"inLanguage\": \"English\",\n"
            + "          \"isbn\": \"0316769487\",\n"
            + "          \"name\": \"The Catcher in the Rye\",\n"
            + "          \"numberOfPages\": \"224\",\n"
            + "          \"sellingOffers\": {\n"
            + "            \"@type\": \"Offer\",\n"
            + "            \"availability\": \"http://schema.org/InStock\",\n"
            + "            \"price\": \"6.99\",\n"
            + "            \"priceCurrency\": \"USD\"\n"
            + "          },\n"
            + "          \"publisher\": \"Little, Brown, and Company\",\n"
            + "          \"aggregateRating\": {\n"
            + "            \"@type\": \"AggregateRating\",\n"
            + "            \"ratingValue\": \"4\",\n"
            + "            \"reviewCount\": \"3077\"\n"
            + "          },\n"
            + "          \"review\": [\n"
            + "            {\n"
            + "              \"@type\": \"Review\",\n"
            + "              \"author\": \"John Doe\",\n"
            + "              \"datePublished\": \"2006-05-04\",\n"
            + "              \"name\": \"A masterpiece of literature\",\n"
            + "              \"reviewBody\": \"I really enjoyed this book. It captures the essential challenge people face as they try make sense of their lives and grow to adulthood.\",\n"
            + "              \"reviewRating\": {\n"
            + "            \"@type\": \"Rating\",\n"
            + "            \"ratingValue\": \"5\"\n"
            + "           }\n"
            + "            },\n"
            + "            {\n"
            + "              \"@type\": \"Review\",\n"
            + "              \"author\": \"Bob Smith\",\n"
            + "              \"datePublished\": \"2006-06-15\",\n"
            + "              \"name\": \"A good read.\",\n"
            + "              \"reviewBody\": \"Catcher in the Rye is a fun book. It's a good book to read.\",\n"
            + "              \"reviewRating\": \"4\"\n"
            + "            }\n"
            + "          ]\n"
            + "        }\n"
            + "}");

        WebPage webPage = (WebPage) new SchemaMapper().map(dataValueObject);
        Book book = WebPage.MainEntity.andThen(as(Book.class)).get(webPage);

        DataValueObject additionalData = (DataValueObject) book.getSchemaOrg4JAdditionalData()
            .getAdditionalData();

        // offers is purposfully misnamed to sellingOffers.  It ends up in additional data and can
        // be parsed if the type can be deduced.
        Offer offer = (Offer) new SchemaMapper()
            .map((DataValueObject) additionalData.getValueFor("sellingOffers"));
        assertEquals(offer.getPrice().getValue().getValue(), 6.99, 0.1);
    }
}
