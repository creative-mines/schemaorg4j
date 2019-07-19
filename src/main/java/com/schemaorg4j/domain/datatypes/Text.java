package com.schemaorg4j.domain.datatypes;

import com.schemaorg4j.util.Lens;
import java.util.Objects;

/**
 * <a href = "https://schema.org/Text">Text</a> type from Schema.org.  Internally maps to
 * {@link java.lang.String}.
 */
public class Text extends DataType {

    private String value;
    private Text nextText;

    public Text() {}

    public Text(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Text getNextText() {
        return nextText;
    }

    public void setNextText(Text nextText) {
        this.nextText = nextText;
    }

    public static Lens<Text, String> Value = new Lens<>(Text::getValue, (c, value) -> {
        c.setValue(value);
        return c;
    });

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Text && Objects
            .equals(((Text) other).getValue(), value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
