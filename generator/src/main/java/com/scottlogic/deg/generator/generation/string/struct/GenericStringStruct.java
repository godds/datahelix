package com.scottlogic.deg.generator.generation.string.struct;

public interface GenericStringStruct<T> {

    T implementation();

    String representation();

    GenericStringStruct<T> union(GenericStringStruct<T> other);

    GenericStringStruct<T> intersect(GenericStringStruct<T> other);
}
