package com.scottlogic.deg.generator.generation.string.struct;

public interface DomainStringStruct<T> {

    T data();

    T interesction(T other);

    T union(T other);

}
