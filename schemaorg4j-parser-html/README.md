# schemaorg4j-parser-html

`schemaorg4j-parser-html` converts Schema.org structured HTML (Microdata) into the intermediate format exposed by `schemaorg4j-parser-core` and relies on that project to generate objects from the `schemaorg4j` project.

`schemaorg4j-parser-html` uses JSoup internally to parse JSON into objects of the appropriate types.

## Example usage

```java
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


DataValueList schemaOrgData = new SchemaOrgHtmlMapper().mapList(EVENT_HTML);
Event event = (Event) new SchemaMapper().map(schemaOrgData).get(0);
assertEquals(event.getName(), "KLT Adult Stewardship Day");
```

## See also

* https://www.w3.org/TR/microdata/