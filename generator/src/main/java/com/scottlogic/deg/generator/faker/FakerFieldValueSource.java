package com.scottlogic.deg.generator.faker;

import com.scottlogic.deg.generator.generation.fieldvaluesources.FieldValueSource;
import com.scottlogic.deg.generator.utils.RandomNumberGenerator;

import java.util.Collections;
import java.util.function.Supplier;

class FakerFieldValueSource implements FieldValueSource {
    private final Supplier<Object> getNextValue;

    FakerFieldValueSource(Supplier<Object> getNextValue) {
        this.getNextValue = getNextValue;
    }

    @Override
    public boolean isFinite() {
        return false;
    }

    @Override
    public long getValueCount() {
        return Long.MAX_VALUE;
    }

    @Override
    public Iterable<Object> generateInterestingValues() {
        return Collections.singletonList(getNextValue.get());
    }

    @Override
    public Iterable<Object> generateAllValues() {
        return () -> new FakerIterator(getNextValue);
    }

    @Override
    public Iterable<Object> generateRandomValues(RandomNumberGenerator randomNumberGenerator) {
        return generateAllValues(); //presume that Faker is random
    }
}