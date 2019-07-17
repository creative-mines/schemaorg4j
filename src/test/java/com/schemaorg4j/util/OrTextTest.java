package com.schemaorg4j.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Assert;
import org.junit.Test;

public class OrTextTest {

    @Test
    public void orTextWithValueReturnsValue() {
        assertEquals(OrText.value(7).getValue().intValue(), 7);
    }

    @Test
    public void orTextWithTextReturnsText() {
        assertEquals(OrText.text("Test").getText(), "Test");
    }

    @Test
    public void equalityWorksForValue() {
        assertEquals(OrText.value(7), OrText.value(7));
    }

    @Test
    public void equalityWorksWithMismatchedValues() {
        assertNotEquals(OrText.value(8), OrText.value(7));
    }

    @Test
    public void equalityWorksWithText() {
        assertEquals(OrText.text("Test"), OrText.text("Test"));
    }

    @Test
    public void mismatchedTextsWillBeUnequal() {
        assertNotEquals(OrText.text("not-anything"), OrText.text("Test"));
    }

    @Test
    public void orTextsHaveIdentityProperty() {
        OrText<Integer> orText = OrText.value(7);
        assertEquals(orText, orText);
    }

    @Test
    public void asTextMapperWorks() {
        OrText.text("Hello").asText(t -> assertEquals(t.getValue(), "Hello"));
    }

    @Test
    public void asValueMapperWorks() {
        OrText.value(7).asValue(n -> assertEquals(n.intValue(), 7));
    }

    @Test
    public void mapWorks() {
        OrText.value(7).map(n -> assertEquals(n.intValue(), 7),
            t -> Assert.fail("Should not call text consumer when text is null"));
    }

    @Test
    public void valueLensReturnsValueWhenPresent() {
        OrTextTestClassForLenses testClass = new OrTextTestClassForLenses(OrText.value(7));
        assertEquals(
            OrTextTestClassForLenses.OrText.andThen(OrText.valueLens()).get(testClass).intValue(),
            7);
    }

    @Test
    public void textLensReturnsTextWhenPresent() {
        OrTextTestClassForLenses testClass = new OrTextTestClassForLenses(OrText.text("Hello"));
        assertEquals(
            OrTextTestClassForLenses.OrText.andThen(OrText.textLens()).get(testClass), "Hello");
    }

    @Test
    public void valueLensReturnsNullWhenNotPresent() {
        OrTextTestClassForLenses testClass = new OrTextTestClassForLenses(OrText.value(7));
        assertNull(
            OrTextTestClassForLenses.OrText.andThen(OrText.textLens()).get(testClass));
    }

    @Test
    public void textLensReturnsNullWhenNotPresent() {
        OrTextTestClassForLenses testClass = new OrTextTestClassForLenses(OrText.text("Hello"));
        assertNull(
            OrTextTestClassForLenses.OrText.andThen(OrText.valueLens()).get(testClass));
    }

    @Test
    public void orTextLensDoesNotAllowSettingValue() {
        OrTextTestClassForLenses testClass = new OrTextTestClassForLenses(OrText.text("Hello"));
        OrTextTestClassForLenses.OrText.andThen(OrText.valueLens()).set(testClass, 7);
        assertEquals(OrTextTestClassForLenses.OrText.andThen(OrText.textLens()).get(testClass),
            "Hello");
    }

    @Test
    public void orTextLensDoesNotAllowSettingText() {
        OrTextTestClassForLenses testClass = new OrTextTestClassForLenses(OrText.text("Hello"));
        OrTextTestClassForLenses.OrText.andThen(OrText.textLens()).set(testClass, "Goodbye");
        assertEquals(OrTextTestClassForLenses.OrText.andThen(OrText.textLens()).get(testClass),
            "Hello");
    }

    private static class OrTextTestClassForLenses {

        public static Lens<OrTextTestClassForLenses, OrText<Integer>> OrText = new Lens<>(
            OrTextTestClassForLenses::getOrText, (t, o) -> {
            t.setOrText(o);
            return t;
        });

        private OrText<Integer> orText;

        public OrTextTestClassForLenses(OrText<Integer> t) {
            this.orText = t;
        }

        public com.schemaorg4j.util.OrText<Integer> getOrText() {
            return orText;
        }

        public void setOrText(OrText<Integer> t) {
            this.orText = t;
        }
    }
}
