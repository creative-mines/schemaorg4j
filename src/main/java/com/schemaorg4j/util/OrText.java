package com.schemaorg4j.util;

import java.util.Objects;
import java.util.function.Consumer;

public class OrText<T> {

    public static <A> Lens<OrText<A>, A> valueLens() {
        return new Lens<>(OrText::getValue, (a, b) -> a);
    }

    public static <A> Lens<OrText<A>, String> textLens() {
        return new Lens<>(OrText::getText, (a, b) -> a);
    }

    private final T value;
    private final String text;

    private OrText(T value, String text) {
        this.value = value;
        this.text = text;
    }

    public static <T> OrText<T> text(String text) {
        return new OrText<>(null, text);
    }

    public static <T> OrText<T> value(T value) {
        return new OrText<>(value, null);
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

    public void asText(Consumer<String> consumer) {
        if (text != null) {
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
        return text;
    }

    public void map(Consumer<T> vc, Consumer<String> sc) {
        if (value != null) {
            vc.accept(value);
        }

        if (text != null) {
            sc.accept(text);
        }
    }
}
