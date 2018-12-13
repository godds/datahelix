package com.scottlogic.deg.generator.generation.field_value_sources;

import com.scottlogic.deg.generator.utils.RandomNumberGenerator;
import com.scottlogic.deg.generator.utils.SupplierBasedIterator;

import java.util.Arrays;
import java.util.List;

public class CannedValuesFieldValueSource implements FieldValueSource {
    private final List<Object> allValues;
    private final List<Object> interestingValues;

    public CannedValuesFieldValueSource(List<Object> values) {
        this.allValues = values;
        this.interestingValues = values;
    }

    public CannedValuesFieldValueSource(List<Object> allValues, List<Object> interestingValues) {
        this.allValues = allValues;
        this.interestingValues = interestingValues;
    }

    public static FieldValueSource of(Object... values) {
        return new CannedValuesFieldValueSource(Arrays.asList(values));
    }

    @Override
    public boolean isFinite() {
        return true;
    }

    @Override
    public long getValueCount() {
        return this.allValues.size();
    }

    @Override
    public Iterable<Object> generateInterestingValues() {
        return this.interestingValues;
    }

    @Override
    public Iterable<Object> generateAllValues() {
        return this.allValues;
    }

    @Override
    public Iterable<Object> generateRandomValues(RandomNumberGenerator randomNumberGenerator) {
        return () -> new SupplierBasedIterator<>(
            () -> this.allValues.get(
                randomNumberGenerator.nextInt(
                    this.allValues.size())));
    }
}
