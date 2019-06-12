package com.scottlogic.deg.generator.restrictions;

import com.scottlogic.deg.common.util.Defaults;

import java.util.Collections;
import java.util.regex.Pattern;

import static com.scottlogic.deg.common.util.Defaults.MAX_STRING_LENGTH;

public class StringRestrictionsFactory {
    public StringRestrictions forStringMatching(Pattern pattern, boolean negate) {
        return new TextualRestrictions(
            null,
            MAX_STRING_LENGTH,
            negate
                ? Collections.emptySet()
                : Collections.singleton(pattern),
            Collections.emptySet(),
            Collections.emptySet(),
            negate
                ? Collections.singleton(pattern)
                : Collections.emptySet(),
            Collections.emptySet()
        );
    }

    public StringRestrictions forStringContaining(Pattern pattern, boolean negate) {
        return new TextualRestrictions(
            null,
            MAX_STRING_LENGTH,
            Collections.emptySet(),
            negate
                ? Collections.emptySet()
                : Collections.singleton(pattern),
            Collections.emptySet(),
            Collections.emptySet(),
            negate
                ? Collections.singleton(pattern)
                : Collections.emptySet()
        );
    }

    public StringRestrictions forLength(int length, boolean negate) {
        return new TextualRestrictions(
            negate ? null : length,
            negate ? MAX_STRING_LENGTH : length,
            Collections.emptySet(),
            Collections.emptySet(),
            negate
                ? Collections.singleton(length)
                : Collections.emptySet(),
            Collections.emptySet(),
            Collections.emptySet()
        );
    }

    public StringRestrictions forMinLength(int length){
        return new TextualRestrictions(
            length,
            MAX_STRING_LENGTH,
            Collections.emptySet(),
            Collections.emptySet(),
            Collections.emptySet(),
            Collections.emptySet(),
            Collections.emptySet()
        );
    }

    public StringRestrictions forMaxLength(int length){
        return new TextualRestrictions(
            null,
            length,
            Collections.emptySet(),
            Collections.emptySet(),
            Collections.emptySet(),
            Collections.emptySet(),
            Collections.emptySet()
        );
    }
}
