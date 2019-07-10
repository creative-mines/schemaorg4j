package com.schemaorg4j.util;

import java.util.function.BiFunction;
import java.util.function.Function;

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
