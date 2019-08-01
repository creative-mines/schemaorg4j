package com.schemaorg4j.domain.datatypes;

/**
 * Holds additional data for any object that could not be parsed into the regular schema
 * defined by Schema.org.  {@link SchemaOrg4JAdditionalData#getAdditionalData()} may return
 * any type of Object.
 *
 * Example from the schemaorg4j-parser-core project:
 * <pre>
 *
 *    DataValueObject dataValueObject = fromJson("{\n"
 *             + "  \"@context\": \"http://schema.org\",\n"
 *             + "  \"@type\": \"WebPage\",\n"
 *             + "  \"breadcrumb\": \"Books > Literature & Fiction > Classics\",\n"
 *             + "  \"mainEntity\":{\n"
 *             + "          \"@type\": \"Book\",\n"
 *             + "          \"author\": \"/author/jd_salinger.html\",\n"
 *             + "          \"bookFormat\": \"http://schema.org/Paperback\",\n"
 *             + "          \"datePublished\": \"1991-05-01\",\n"
 *             + "          \"image\": \"catcher-in-the-rye-book-cover.jpg\",\n"
 *             + "          \"inLanguage\": \"English\",\n"
 *             + "          \"isbn\": \"0316769487\",\n"
 *             + "          \"name\": \"The Catcher in the Rye\",\n"
 *             + "          \"numberOfPages\": \"224\",\n"
 *             + "          \"sellingOffers\": {\n"
 *             + "            \"@type\": \"Offer\",\n"
 *             + "            \"availability\": \"http://schema.org/InStock\",\n"
 *             + "            \"price\": \"6.99\",\n"
 *             + "            \"priceCurrency\": \"USD\"\n"
 *             + "          },\n"
 *             + "        }\n"
 *             + "}");
 *
 *    WebPage webPage = (WebPage) new SchemaMapper().map(dataValueObject);
 *    Book book = WebPage.MainEntity.andThen(as(Book.class)).get(webPage);
 *
 *    DataValueObject additionalData = (DataValueObject) book.getSchemaOrg4JAdditionalData().getAdditionalData();
 *
 *    // Offers is purposfully misnamed to sellingOffers.  It ends up in additional data and can
 *    // be parsed if the type can be deduced.
 *    Offer offer = (Offer) new SchemaMapper().map(
 *        (DataValueObject) additionalData.getValueFor("sellingOffers"));
 *    assertEquals(offer.getPrice().getValue().getValue(), 6.99, 0.1);
 * </pre>
 */
public class SchemaOrg4JAdditionalData {

    private Object additionalData;

    public Object getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Object additionalData) {
        this.additionalData = additionalData;
    }
}
