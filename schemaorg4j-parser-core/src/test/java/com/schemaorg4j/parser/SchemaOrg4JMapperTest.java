package com.schemaorg4j.parser;

import static com.schemaorg4j.parser.TestUtil.fromJson;
import static com.schemaorg4j.util.Lens.as;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.schemaorg4j.domain.AggregateOffer;
import com.schemaorg4j.domain.Book;
import com.schemaorg4j.domain.Event;
import com.schemaorg4j.domain.MusicEvent;
import com.schemaorg4j.domain.Person;
import com.schemaorg4j.domain.Place;
import com.schemaorg4j.domain.PostalAddress;
import com.schemaorg4j.domain.Thing;
import com.schemaorg4j.domain.TouristAttraction;
import com.schemaorg4j.domain.combo.DateOrDateTime;
import com.schemaorg4j.domain.combo.OrganizationOrPerson;
import com.schemaorg4j.domain.combo.PlaceOrPostalAddressOrText;
import com.schemaorg4j.domain.enums.BookFormatTypeEnumMembers;
import com.schemaorg4j.parser.core.SchemaMapper;
import com.schemaorg4j.parser.core.domain.DataValueObject;
import com.schemaorg4j.util.OrText;
import java.io.IOException;
import java.util.stream.Collectors;
import org.junit.Test;

public class SchemaOrg4JMapperTest {

    @Test
    public void testParsingSimplestPossibleEvent() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"name\": \"Shostakovich Leningrad\"\n"
            + "}");

        Thing thing = new SchemaMapper().map(dataValueObject);
        assertTrue(thing instanceof MusicEvent);

        MusicEvent musicEvent = (MusicEvent) thing;
        assertEquals(musicEvent.getName(), "Shostakovich Leningrad");

    }

    @Test
    public void testParsingListField() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"location\": {\n"
            + "    \"@type\": \"MusicVenue\",\n"
            + "    \"name\": \"Chicago Symphony Center\",\n"
            + "    \"address\": \"220 S. Michigan Ave, Chicago, Illinois, USA\"\n"
            + "  },\n"
            + "  \"name\": \"Shostakovich Leningrad\",\n"
            + "  \"offers\": {\n"
            + "    \"@type\": \"Offer\",\n"
            + "    \"url\": \"/examples/ticket/12341234\",\n"
            + "    \"price\": \"40\",\n"
            + "    \"priceCurrency\": \"USD\",\n"
            + "    \"availability\": \"http://schema.org/InStock\"\n"
            + "  },\n"
            + "  \"performer\": [\n"
            + "    {\n"
            + "      \"@type\": \"MusicGroup\",\n"
            + "      \"name\": \"Chicago Symphony Orchestra\",\n"
            + "      \"sameAs\": [\n"
            + "        \"http://cso.org/\",\n"
            + "        \"http://en.wikipedia.org/wiki/Chicago_Symphony_Orchestra\"\n"
            + "      ]\n"
            + "    },\n"
            + "    {\n"
            + "      \"@type\": \"Person\",\n"
            + "      \"image\": \"/examples/jvanzweden_s.jpg\",\n"
            + "      \"name\": \"Jaap van Zweden\",\n"
            + "      \"sameAs\": \"http://www.jaapvanzweden.com/\"\n"
            + "    }\n"
            + "  ],\n"
            + "  \"startDate\": \"2014-05-23T20:00\",\n"
            + "  \"workPerformed\": [\n"
            + "    {\n"
            + "      \"@type\": \"CreativeWork\",\n"
            + "      \"name\": \"Britten Four Sea Interludes and Passacaglia from Peter Grimes\",\n"
            + "      \"sameAs\": \"http://en.wikipedia.org/wiki/Peter_Grimes\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"@type\": \"CreativeWork\",\n"
            + "      \"name\": \"Shostakovich Symphony No. 7 (Leningrad)\",\n"
            + "      \"sameAs\": \"http://en.wikipedia.org/wiki/Symphony_No._7_(Shostakovich)\"\n"
            + "    }\n"
            + "  ]\n"
            + "}");

        MusicEvent musicEvent = (MusicEvent) new SchemaMapper().map(dataValueObject);

        assertEquals(musicEvent.getPerformer().asOrganizationOrPersonList().get(0).getOrganization().getName(), "Chicago Symphony Orchestra");
        assertEquals(musicEvent.getPerformer().asOrganizationOrPersonList().get(1).getPerson().getName(), "Jaap van Zweden");
    }

    @Test
    public void testParsingEventWithDateAndUrl() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"Event\",\n"
            + "  \"name\": \"Miami Heat at Philadelphia 76ers - Game 3 (Home Game 1)\",\n"
            + "  \"location\": {\n"
            + "    \"@type\": \"Place\",\n"
            + "    \"address\": {\n"
            + "      \"@type\": \"PostalAddress\",\n"
            + "      \"addressLocality\": \"Philadelphia\",\n"
            + "      \"addressRegion\": \"PA\"\n"
            + "    },\n"
            + "    \"url\": \"wells-fargo-center.html\"\n"
            + "  },\n"
            + "  \"offers\": {\n"
            + "    \"@type\": \"AggregateOffer\",\n"
            + "    \"lowPrice\": \"$35\",\n"
            + "    \"offerCount\": \"1938\"\n"
            + "  },\n"
            + "  \"startDate\": \"2016-04-21T20:00\",\n"
            + "  \"url\": \"nba-miami-philidelphia-game3.html\"\n"
            + "}");

        Event event = (Event) new SchemaMapper().map(dataValueObject);

        // Urls are a basic type
        assertEquals(Event.Url.get(event).getValue(), "nba-miami-philidelphia-game3.html");

        // This is an or type which could be either a date or date time, in this case it's a
        // datetime
        assertEquals(Event.StartDate.get(event).getDateTime().toString(), "2016-04-21T20:00Z[UTC]");

        // This is an aggregate offer instead of a regular offer
        assertEquals(
            Event.Offers.andThen(as(AggregateOffer.class)).andThen(AggregateOffer.OfferCount)
                .get(event).getValue().intValue(), 1938);

        // Getting deeply nested through orText
        assertEquals(Event.Location
            .andThen(PlaceOrPostalAddressOrText.Place)
            .andThen(Place.Address)
            .andThen(OrText.Value())
            .andThen(PostalAddress.AddressRegion)
            .get(event)
            .getValue(), "PA");
    }

    @Test
    public void testWithMultipleStringNode() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "        \"@context\": \"http://schema.org\",\n"
            + "        \"@type\": \"TouristAttraction\",\n"
            + "        \"name\": \"Musée Marmottan Monet\",\n"
            + "        \"description\": \"It's a museum of Impressionism and french ninenteeth art.\",\n"
            + "        \"event\": {\n"
            + "                \"@type\": \"Event\",\n"
            + "                \"about\": [\"Hodler\",\"Monet\",\"Munch\"],\n"
            + "                \"name\": \"Peindre l'impossible\",\n"
            + "                \"startDate\": \"2016-09-15\",\n"
            + "                \"endDate\": \"2017-01-22\"\n"
            + "        }\n"
            + "}");

        TouristAttraction attraction = (TouristAttraction) new SchemaMapper().map(dataValueObject);

        assertEquals(TouristAttraction.Event.andThen(Event.StartDate).andThen(DateOrDateTime.Date).get(attraction).getValue().toString(), "2016-09-15");

        Event event = attraction.getEvent();
        assertEquals(event.getAbout().getSimpleValue(), "Hodler");
        assertEquals(event.getAbout().getNextThing().getSimpleValue(), "Monet");
        assertEquals(event.getAbout().getNextThing().getNextThing().getSimpleValue(), "Munch");
    }

    @Test
    public void testAsListInterfaceWorks() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "        \"@context\": \"http://schema.org\",\n"
            + "        \"@type\": \"TouristAttraction\",\n"
            + "        \"name\": \"Musée Marmottan Monet\",\n"
            + "        \"description\": \"It's a museum of Impressionism and french ninenteeth art.\",\n"
            + "        \"event\": {\n"
            + "                \"@type\": \"Event\",\n"
            + "                \"about\": [\"Hodler\",\"Monet\",\"Munch\"],\n"
            + "                \"name\": \"Peindre l'impossible\",\n"
            + "                \"startDate\": \"2016-09-15\",\n"
            + "                \"endDate\": \"2017-01-22\"\n"
            + "        }\n"
            + "}");

        TouristAttraction attraction = (TouristAttraction) new SchemaMapper().map(dataValueObject);

        assertEquals(TouristAttraction.Event.andThen(Event.StartDate).andThen(DateOrDateTime.Date).get(attraction).getValue().toString(), "2016-09-15");

        Event event = attraction.getEvent();
        assertEquals(event.getAbout().asThingList().stream().map(Thing::getSimpleValue).collect(
            Collectors.joining(", ")), "Hodler, Monet, Munch");
    }


    @Test
    public void testEnums() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"Book\",\n"
            + "  \"accessibilityAPI\": \"ARIA\",\n"
            + "  \"accessibilityControl\": [\n"
            + "    \"fullKeyboardControl\",\n"
            + "    \"fullMouseControl\"\n"
            + "  ],\n"
            + "  \"accessibilityFeature\": [\n"
            + "    \"largePrint/CSSEnabled\",\n"
            + "    \"highContrast/CSSEnabled\",\n"
            + "    \"resizeText/CSSEnabled\",\n"
            + "    \"displayTransformability\",\n"
            + "    \"longDescription\",\n"
            + "    \"alternativeText\"\n"
            + "  ],\n"
            + "  \"accessibilityHazard\": [\n"
            + "    \"noFlashingHazard\",\n"
            + "    \"noMotionSimulationHazard\",\n"
            + "    \"noSoundHazard\"\n"
            + "  ],\n"
            + "  \"aggregateRating\": {\n"
            + "    \"@type\": \"AggregateRating\",\n"
            + "    \"reviewCount\": \"0\"\n"
            + "  },\n"
            + "  \"bookFormat\": \"EBook/DAISY3\",\n"
            + "  \"copyrightHolder\": {\n"
            + "    \"@type\": \"Organization\",\n"
            + "    \"name\": \"Holt, Rinehart and Winston\"\n"
            + "  },\n"
            + "  \"copyrightYear\": \"2007\",\n"
            + "  \"description\": \"NIMAC-sourced textbook\",\n"
            + "  \"genre\": \"Educational Materials\",\n"
            + "  \"inLanguage\": \"en-US\",\n"
            + "  \"isFamilyFriendly\": \"true\",\n"
            + "  \"isbn\": \"9780030426599\",\n"
            + "  \"name\": \"Holt Physical Science\",\n"
            + "  \"numberOfPages\": \"598\",\n"
            + "  \"publisher\": {\n"
            + "    \"@type\": \"Organization\",\n"
            + "    \"name\": \"Holt, Rinehart and Winston\"\n"
            + "  }\n"
            + "}");


        Book book = (Book) new SchemaMapper().map(dataValueObject);
        assertEquals(book.getBookFormat().getEnumMembers(), BookFormatTypeEnumMembers.EBook);
        assertEquals(book.getAccessibilityFeature(), "largePrint/CSSEnabled");
        assertEquals(book.getAccessibilityFeatureData().getNextText().getValue(), "highContrast/CSSEnabled");
    }

    @Test
    public void listsOfOrTypesCanBeHandled() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"inLanguage\": [{\"@type\": \"Language\"}, \"en-Us\"]\n"
            + "}");

        MusicEvent event = (MusicEvent) new SchemaMapper().map(dataValueObject);
        assertEquals(event.getInLanguage().getNextOrText().getText(), "en-Us");
    }

    @Test
    public void complexOrTypesCanHaveSimpleValues() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"Book\",\n"
            + "  \"publisher\": \"Little, Brown, and Company\"\n"
            + "}");

        Book book = (Book) new SchemaMapper().map(dataValueObject);
        assertEquals(book.getPublisher().getSimpleValue(), "Little, Brown, and Company");
        assertEquals((Book.Publisher).oAndThen(OrganizationOrPerson.SimpleValue).get(book), "Little, Brown, and Company");
    }

    @Test
    public void objectTypesThatAreNotThingCanHaveSimpleValues() throws IOException {
        DataValueObject dataValueObject = fromJson("{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"Book\",\n"
            + "  \"illustrator\": \"Little, Brown, and Company\"\n"
            + "}");

        Book book = (Book) new SchemaMapper().map(dataValueObject);
        assertEquals(book.getIllustrator().getSimpleValue(), "Little, Brown, and Company");
        assertEquals(Book.Illustrator.oAndThen(as(Thing.class)).oAndThen(Person.SimpleValue).get(book), "Little, Brown, and Company");
    }
}
