package com.scottlogic.deg.generator.generation.string;

import com.scottlogic.deg.generator.generation.fieldvaluesources.FieldValueSource;
import com.scottlogic.deg.generator.utils.RandomNumberGenerator;

public interface StringGenerator {
    StringGenerator intersect(StringGenerator stringGenerator);
    StringGenerator complement();

    boolean isFinite();
    long getValueCount();
    boolean match(String subject);

    Iterable<String> generateInterestingValues();

    Iterable<String> generateAllValues();

    Iterable<String> generateRandomValues(RandomNumberGenerator randomNumberGenerator);

    default FieldValueSource asFieldValueSource() {
        return new StringGeneratorAsFieldValueSource(this);
    }


}
