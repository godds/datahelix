package com.scottlogic.deg.generator.generation.string.struct;

public interface StringGeneratorStruct<T, D extends DomainStringStruct<T>> {

    D domain();

    RegexStruct regex();

}
