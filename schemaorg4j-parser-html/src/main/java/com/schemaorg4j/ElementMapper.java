package com.schemaorg4j;

import static com.schemaorg4j.MicrodataConstants.ITEMPROP;
import static com.schemaorg4j.MicrodataConstants.ITEMSCOPE;
import static com.schemaorg4j.MicrodataConstants.ITEMTYPE;
import static java.util.stream.Collectors.joining;

import com.schemaorg4j.parser.core.domain.DataValue;
import com.schemaorg4j.parser.core.domain.DataValueList;
import com.schemaorg4j.parser.core.domain.DataValueObject;
import com.schemaorg4j.parser.core.domain.DataValueString;
import java.util.Objects;
import org.jsoup.nodes.Element;

public class ElementMapper {

    public DataValueObject map(Element element) {
        DataValueObject valueObject = new DataValueObject(element.attr(ITEMTYPE));

        element.children().select(String.format("[%s]:not([%<s] [%<s])", ITEMPROP))
            .forEach(fieldElement -> {
                if (fieldElement.equals(element)) {
                    return;
                }

                String fieldName = fieldElement.attr(ITEMPROP);

                if (fieldElement.hasAttr(ITEMTYPE)) {
                    DataValueObject subObject = this.map(fieldElement);

                    // If the original value object already has a field of this type, then it needs
                    // to be converted into a list.  Otherwise it can just be stored

                    DataValue existingValueForField = valueObject.getValueFor(fieldName);
                    if (existingValueForField instanceof DataValueObject
                        || existingValueForField instanceof DataValueString) {
                        DataValueList valueList = new DataValueList();
                        valueList.addValue(existingValueForField);
                        valueList.addValue(subObject);
                        valueObject.putField(fieldName, valueList);
                    } else if (existingValueForField instanceof DataValueList) {
                        ((DataValueList) existingValueForField).addValue(subObject);
                    } else {
                        valueObject.putField(fieldName, subObject);
                    }
                } else {
                    DataValueString valueString = new DataValueString(getValue(fieldElement));
                    valueObject.putField(fieldName, valueString);

                    // All of these properties must necessarily belong to the object currently being
                    // processed
                    fieldElement.children().stream()
                        .filter(
                            embeddedPropertyElement -> embeddedPropertyElement.hasAttr(ITEMPROP))
                        .forEach(embeddedPropertyElement -> {
                            String embeddedElementFieldName = embeddedPropertyElement
                                .attr(ITEMPROP);
                            DataValueString embeddedElementFieldValue = new DataValueString(
                                getValue(embeddedPropertyElement));
                            valueObject
                                .putField(embeddedElementFieldName, embeddedElementFieldValue);
                        });
                }
            });

        return valueObject;
    }

    private String getValue(Element element) {
        if (element.hasAttr("content")) {
            return element.attr("content");
        }

        if (Objects.equals(element.tagName(), "a") && element.hasAttr("href")) {
            return element.attr("href");
        }

        if (Objects.equals(element.tagName(), "img") && element.hasAttr("src")) {
            return element.attr("src");
        }

        String text = element.text();

        if (element.getElementsByTag("a").isEmpty()) {
            return text;
        }

        return String.format("%s %s", text,
            element.getElementsByTag("a")
                .stream()
                .filter(link -> !link.hasAttr(ITEMPROP))
                .map(link -> link.attr("href"))
                .collect(joining(" "))).trim();
    }
}
