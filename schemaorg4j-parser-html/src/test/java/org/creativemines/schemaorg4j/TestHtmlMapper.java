package org.creativemines.schemaorg4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.creativemines.schemaorg4j.SchemaOrgHtmlMapper;
import org.creativemines.schemaorg4j.domain.Event;
import org.creativemines.schemaorg4j.domain.MusicEvent;
import org.creativemines.schemaorg4j.parser.core.SchemaMapper;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueList;
import org.junit.Test;

public class TestHtmlMapper {

    String HTML = "    <div itemscope=\"\" itemtype=\"http://schema.org/MusicEvent\">\n"
        + "      <div itemprop=\"location\" itemscope=\"\" itemtype=\"http://schema.org/MusicVenue\">\n"
        + "        <meta itemprop=\"name\" content=\"Chicago Symphony Center\"/>\n"
        + "        <link itemprop=\"sameAs\" href=\"http://en.wikipedia.org/wiki/Symphony_Center\"/>\n"
        + "        <meta itemprop=\"address\" content=\"220 S. Michigan Ave, Chicago, Illinois, USA\"/>\n"
        + "      </div>\n"
        + "      <div itemprop=\"offers\" itemscope=\"\" itemtype=\"http://schema.org/Offer\">\n"
        + "        <link itemprop=\"url\" href=\"/examples/ticket/12341234\" />\n"
        + "        <meta itemprop=\"price\" content=\"40\"/>\n"
        + "        <meta itemprop=\"priceCurrency\" content=\"USD\" />\n"
        + "        <link itemprop=\"availability\" href=\"http://schema.org/InStock\"/>\n"
        + "      </div>\n"
        + "      <h2 itemprop=\"name\">Shostakovich Leningrad</h2>\n"
        + "      <div>\n"
        + "        <div itemprop=\"startDate\" content=\"2014-05-23T20:00\">May<span>23</span></div>\n"
        + "        <div>8:00 PM</div>\n"
        + "        <div>\n"
        + "          <strong>Britten, Shostakovich</strong>\n"
        + "        </div>\n"
        + "      </div>\n"
        + "      <div>\n"
        + "        <p>Jaap van Zweden conducts two World War II-era pieces showcasing the glorious sound of the CSO.</p>\n"
        + "      </div>\n"
        + "      <div>\n"
        + "        <h3>Program</h3>\n"
        + "        <ul>\n"
        + "          <li itemprop=\"workPerformed\" itemscope=\"\" itemtype=\"http://schema.org/CreativeWork\">\n"
        + "            <link itemprop=\"sameAs\" href=\"http://en.wikipedia.org/wiki/Peter_Grimes\" />\n"
        + "            <span itemprop=\"name\"><strong>Britten</strong> Four Sea Interludes and Passacaglia from <em itemprop=\"name\">Peter Grimes</em></span>\n"
        + "      </li>\n"
        + "          <li itemprop=\"workPerformed\" itemscope=\"\" itemtype=\"http://schema.org/CreativeWork\">\n"
        + "          <link itemprop=\"sameAs\" href=\"http://en.wikipedia.org/wiki/Symphony_No._7_(Shostakovich)\" />\n"
        + "          <span itemprop=\"name\"><strong>Shostakovich</strong> Symphony No. 7 <em>(Leningrad)</em></span>\n"
        + "      </li>\n"
        + "        </ul>\n"
        + "      </div>\n"
        + "      <div>\n"
        + "        <h3>Performers</h3>\n"
        + "        <div itemprop=\"performer\" itemscope=\"\" itemtype=\"http://schema.org/MusicGroup\">\n"
        + "          <img src=\"/examples/cso_c_logo_s.jpg\" alt=\"Chicago Symphony Orchestra\" />\n"
        + "          <link itemprop=\"sameAs\" href=\"http://cso.org/\" />\n"
        + "          <link itemprop=\"sameAs\" href=\"http://en.wikipedia.org/wiki/Chicago_Symphony_Orchestra\" />\n"
        + "          <div>\n"
        + "            <a href=\"examples/Performer?id=4434\"><span itemprop=\"name\">Chicago Symphony Orchestra</span></a>\n"
        + "          </div>\n"
        + "        </div>\n"
        + "        <div itemprop=\"performer\" itemscope=\"\" itemtype=\"http://schema.org/Person\">\n"
        + "          <link itemprop=\"sameAs\" href=\"http://www.jaapvanzweden.com/\" />\n"
        + "          <img itemprop=\"image\" src=\"/examples/jvanzweden_s.jpg\" alt=\"Jaap van Zweden\"/>\n"
        + "          <div>\n"
        + "            <a href=\"/examples/Performer.aspx?id=11324\"><span itemprop=\"name\">Jaap van Zweden</span></a>\n"
        + "          </div>\n"
        + "          <div>conductor</div>\n"
        + "        </div>\n"
        + "      </div>\n"
        + "    </div>";

    @Test
    public void testHtmlMapperWithList() throws IOException {
        DataValueList schemaOrgData = new SchemaOrgHtmlMapper()
            .mapList(HTML);

        MusicEvent musicEvent = (MusicEvent) new SchemaMapper().map(schemaOrgData).get(0);
        assertEquals(musicEvent.getPerformer().getNextOrganizationOrPerson().getPerson().getName(),
            "Jaap van Zweden");
    }

    String EVENT_HTML =
        "<div itemscope=\"itemscope\" itemtype=\"http://schema.org/Event\" id=\"mn-event7191\" class=\"mn-listing mn-listingevent mn-event-catg3 mn-event-catg9\">\n"
            + "    <meta itemprop=\"eventStatus\" content=\"EventScheduled\">\n"
            + "    <div class=\"mn-listingcontent\">\n"
            + "\t    <div class=\"mn-title\" itemprop=\"name\">\n"
            + "            <a itemprop=\"url\" href=\"http://www.keweenaw.org/events/details/klt-adult-stewardship-day-7191\">KLT Adult Stewardship Day</a>\n"
            + "\t    </div>  \n"
            + "\t    <div class=\"mn-date\">\n"
            + "\t\t\t<div class=\"mn-dateday\">\t    \n"
            + "\t\t\t\t\t    <span itemprop=\"startDate\" content=\"2019-07-25T18:00\">Thursday Jul 25, 2019</span>\n"
            + "                        <meta itemprop=\"endDate\" content=\"2019-07-25T20:00\">\n"
            + "\t\t\t</div>\n"
            + "\t    </div>\n"
            + "\t    <div class=\"mn-listingcontent-innercontainer\">\n"
            + "            <div class=\"mn-image mn-image-empty\"></div>\n"
            + "            <div itemprop=\"description\" class=\"mn-desc\">\n"
            + "                \n"
            + "                <span class=\"mn-read-more\">...</span>&nbsp;<a class=\"mn-read-more\" href=\"http://www.keweenaw.org/events/details/klt-adult-stewardship-day-7191\" rel=\"nofollow\">read more</a>\n"
            + "            </div>\n"
            + "            <div class=\"mn-category\">Categories: Community, Clubs/Organizations</div>\n"
            + "\t    \t    </div>\n"
            + "\t    <div class=\"mn-clear\"></div>\n"
            + "\t</div>\n"
            + "</div>";

    @Test
    public void htmlContentsWillBeScrubbedForATerminalField() throws IOException {
        DataValueList schemaOrgData = new SchemaOrgHtmlMapper()
            .mapList(EVENT_HTML);

        Event event = (Event) new SchemaMapper().map(schemaOrgData).get(0);
        assertEquals(event.getName(), "KLT Adult Stewardship Day");
    }

    @Test
    public void htmlContentsInATerminalFieldWillBeConsideredForTheFieldAbove() throws IOException {
        DataValueList schemaOrgData = new SchemaOrgHtmlMapper()
            .mapList(EVENT_HTML);

        Event event = (Event) new SchemaMapper().map(schemaOrgData).get(0);
        assertEquals(event.getUrl(),
            "http://www.keweenaw.org/events/details/klt-adult-stewardship-day-7191");
    }

    @Test
    public void textContentWillBeDisplayedInATerminalField() throws IOException {
        DataValueList schemaOrgData = new SchemaOrgHtmlMapper()
            .mapList(EVENT_HTML);

        Event event = (Event) new SchemaMapper().map(schemaOrgData).get(0);
        assertTrue(event.getDescription().contains("... read more"));
    }

    @Test
    public void embeddedLinksWillBeDisplayedInATerminalFieldIfTheyDoNotHaveItemProps()
        throws IOException {
        DataValueList schemaOrgData = new SchemaOrgHtmlMapper()
            .mapList(EVENT_HTML);

        Event event = (Event) new SchemaMapper().map(schemaOrgData).get(0);
        assertTrue(event.getDescription()
            .contains("http://www.keweenaw.org/events/details/klt-adult-stewardship-day-7191"));
    }
}
