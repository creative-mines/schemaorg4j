package com.schemaorg4j.util;

import com.schemaorg4j.domain.datatypes.SchemaOrg4JAdditionalData;
import com.schemaorg4j.domain.datatypes.Text;
import com.schemaorg4j.domain.error.SchemaOrg4JError;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class OrText<T> {

    public static <A> Lens<OrText<A>, A> Value() {
        return new Lens<>(OrText::getValue, (a, b) -> a);
    }

    public static <A> Lens<OrText<A>, String> Text() {
        return new Lens<>(OrText::getText, (a, b) -> a);
    }

    public static <A> Lens<OrText<A>, Text> TextData() {
        return new Lens<>(OrText::getTextData, (a, b) -> a);
    }

    public static final <A> Lens<OrText<A>, OrText<A>> Next() {
        return new Lens<>(OrText::getNextOrText, (a, b) -> a);
    }

    private final T value;
    private final Text text;
    private OrText<T> nextOrText;
    private SchemaOrg4JAdditionalData schemaOrg4JAdditionalData;
    private List<SchemaOrg4JError> schemaOrg4JErrors;

    private OrText(T value, Text text) {
        this.value = value;
        this.text = text;
    }

    private OrText(T value, String text) {
        this.value = value;
        this.text = new Text();
        this.text.setValue(text);
    }

    public static <T> OrText<T> text(String text) {
        return new OrText<>(null, text);
    }

    public static <T> OrText<T> value(T value) {
        return new OrText<>(value, new Text());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof OrText)) {
            return false;
        }

        OrText otherOrText = ((OrText) other);

        return Objects.equals(otherOrText.getValue(), this.getValue()) &&
            Objects.equals(otherOrText.getText(), this.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getText());
    }

    public void asText(Consumer<Text> consumer) {
        if (text != null && text.getValue() != null) {
            consumer.accept(text);
        }
    }

    public void asValue(Consumer<T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    public T getValue() {
        return value;
    }

    public String getText() {
        return text.getValue();
    }

    public void map(Consumer<T> vc, Consumer<Text> sc) {
        if (value != null) {
            vc.accept(value);
        }

        if (text != null && text.getValue() != null) {
            sc.accept(text);
        }
    }

    public OrText<T> getNextOrText() {
        return nextOrText;
    }

    public void setNextOrText(OrText<T> nextOrText) {
        this.nextOrText = nextOrText;
    }

    public Text getTextData() {
        return text;
    }

    public SchemaOrg4JAdditionalData getSchemaOrg4JAdditionalData() {
        return schemaOrg4JAdditionalData;
    }

    public void setSchemaOrg4JAdditionalData(
        SchemaOrg4JAdditionalData schemaOrg4JAdditionalData) {
        this.schemaOrg4JAdditionalData = schemaOrg4JAdditionalData;
    }

    public List<SchemaOrg4JError> getSchemaOrg4JErrors() {
        return schemaOrg4JErrors;
    }

    public void setSchemaOrg4JErrors(
        List<SchemaOrg4JError> schemaOrg4JErrors) {
        this.schemaOrg4JErrors = schemaOrg4JErrors;
    }
}
