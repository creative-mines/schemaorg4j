package com.schemaorg4j;

import static com.schemaorg4j.MicrodataConstants.ITEMSCOPE;

import com.schemaorg4j.parser.core.domain.DataValueList;
import com.schemaorg4j.parser.core.domain.DataValueObject;
import java.io.IOException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SchemaOrgHtmlMapper {

    private static final int STANDARD_TIMEOUT = 30;

    private final int timeout;
    private final ElementMapper elementMapper;

    public SchemaOrgHtmlMapper() {
       this(STANDARD_TIMEOUT);
    }

    public SchemaOrgHtmlMapper(int timeout) {
        this.timeout = timeout;
        this.elementMapper = new ElementMapper();
    }

    public DataValueList mapList(Document document) throws IOException {
        DataValueList dataValueList = new DataValueList();

        document.select(String.format("[%s]:not([%<s] [%<s])", ITEMSCOPE))
            .stream()
            .map(elementMapper::map)
            .forEach(dataValueList::addValue);

        return dataValueList;
    }

    public DataValueList mapList(String data) throws IOException {
        return mapList(Jsoup.parse(data));
    }

    public DataValueList crawl(URL url) throws IOException {
        Document document = Jsoup.parse(url, timeout);
        return mapList(document);
    }

    public DataValueList crawl(String url) throws IOException {
        return crawl(new URL(url));
    }
}
