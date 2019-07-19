package com.schemaorg4j.util;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Lenses are generated internally for all Schema.org types to allow simple traversal of objects.
 * Lensing is a functional programming concept which allows traversal and modification of deeply
 * nested data structures. Lensing is usually implemented with immutability in mind, but Lenses in
 * this project **MUTATE** the underlying structure (see generated setter lenses in this project for
 * details).
 *
 * This lensing implementation sourced from <a href = "http://davids-code.blogspot.com/2014/02/immutable-domain-and-lenses-in-java-8.html">http://davids-code.blogspot.com/2014/02/immutable-domain-and-lenses-in-java-8.html</a>
 *
 * Example with simple lensing:
 * <pre>
 *    assertEquals(Event.StartDate.get(event).getDateTime().toString(), "2016-04-21T20:00Z[UTC]");
 * </pre>
 *
 * Example of lensing through a parameterized or type
 * <pre>
 *    assertEquals(Event.Location
 *             .andThen(PlaceOrPostalAddressOrText.Place)
 *             .andThen(Place.Address)
 *             .andThen(OrText.Text())
 *             .get(event)
 *             .getValue(), "1600 Pennsylvania Ave NW, Washington, DC 20500");
 * </pre>
 *
 * Example of lensing through a few complex or types
 * <pre>
 *    assertEquals(Event.Location
 *             .andThen(PlaceOrPostalAddressOrText.Place)
 *             .andThen(Place.Address)
 *             .andThen(OrText.Value())
 *             .andThen(PostalAddress.AddressRegion)
 *             .get(event)
 *             .getValue(), "PA");
 * </pre>
 *
 * @param <A> Source type
 * @param <B> Destination type
 *
 * @see <a href = "https://www.schoolofhaskell.com/school/to-infinity-and-beyond/pick-of-the-week/basic-lensing">An
 * introduction to Lenses using Haskell</a>
 * @see <a href = "http://davids-code.blogspot.com/2014/02/immutable-domain-and-lenses-in-java-8.html">Example
 * of lensing in Java8</a>
 * @see <a href = "https://gist.github.com/mathieuancelin/bb30a104c17037e34f0b">Another lensing
 * implementation</a>
 */
public class Lens<A, B> {

    private Function<A, B> fget;
    private BiFunction<A, B, A> fset;

    public Lens(Function<A, B> fget, BiFunction<A, B, A> fset) {
        this.fget = fget;
        this.fset = fset;
    }

    public B get(A a) {
        if (a != null) {
            return fget.apply(a);
        }
        return null;
    }

    public A set(A a, B b) {
        if (a != null) {
            return fset.apply(a, b);
        }
        return null;
    }

    public A mod(A a, Function<B, B> f) {
        return set(a, f.apply(get(a)));
    }

    public <C> Lens<C, B> compose(final Lens<C, A> that) {
        return new Lens<C, B>(c -> get(that.get(c)), (c, b) -> that.mod(c, (a) -> set(a, b)));
    }

    public <C> Lens<A, C> andThen(Lens<B, C> that) {
        return that.compose(this);
    }

    public static <A, B> Lens<A, B> as(Class<B> clazz) {
        return new Lens<>(b -> {
            if (clazz.isAssignableFrom(b.getClass())) {
                return clazz.cast(b);
            }
            return null;
        }, (a, b) -> a);
    }
}
