package com.scottlogic.deg.generator.fieldspecs;

import com.google.common.collect.ImmutableMap;
import com.scottlogic.deg.common.profile.Field;
import com.scottlogic.deg.common.profile.ProfileFields;
import com.scottlogic.deg.generator.restrictions.AnyTypeRestriction;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

class RowSpecMergerTest {
    RowSpecMerger rowSpecMerger = new RowSpecMerger(new FieldSpecMerger());

    FieldSpec isNull = FieldSpec.mustBeNull();
    FieldSpec notNull = FieldSpec.Empty.withNotNull().withTypeRestrictions(new AnyTypeRestriction());
    Field A = new Field("A");
    Field B = new Field("B");
    ProfileFields fields = new ProfileFields(Arrays.asList(A, B));

    @Test
    void merge_notContradictoryForField() {
        RowSpec left = new RowSpec(fields, ImmutableMap.of(A, isNull));
        RowSpec right = new RowSpec(fields, ImmutableMap.of(A, isNull));

        RowSpec merged = rowSpecMerger.merge(left, right).get();

        assertEquals(merged.getSpecForField(A), isNull);
    }

    @Test
    void merge_contradictoryForField() {
        RowSpec left = new RowSpec(fields, ImmutableMap.of(A, isNull));
        RowSpec right = new RowSpec(fields, ImmutableMap.of(A, notNull));

        Optional<RowSpec> merged = rowSpecMerger.merge(left, right);

        assertEquals(merged, Optional.empty());
    }


    @Test
    void merge_twoFields() {
        RowSpec left = new RowSpec(fields, ImmutableMap.of(A, isNull));
        RowSpec right = new RowSpec(fields, ImmutableMap.of(B, notNull));

        RowSpec merged = rowSpecMerger.merge(left, right).get();
        assertEquals(merged.getSpecForField(A), (isNull));
        assertEquals(merged.getSpecForField(B), (notNull));
    }
}