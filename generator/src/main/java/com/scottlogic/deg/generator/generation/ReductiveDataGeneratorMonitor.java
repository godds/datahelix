/*
 * Copyright 2019 Scott Logic Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.scottlogic.deg.generator.generation;

import com.google.inject.Inject;
import com.scottlogic.deg.common.profile.Field;
import com.scottlogic.deg.generator.fieldspecs.FieldSpec;
import com.scottlogic.deg.generator.walker.reductive.ReductiveState;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ReductiveDataGeneratorMonitor implements DataGeneratorMonitor {
    final PrintWriter writer;

    private List<String> linesToPrintAtEndOfGeneration = new ArrayList<>();

    @Inject
    ReductiveDataGeneratorMonitor(PrintWriter writer) {
        this.writer = writer;
    }

    public void endGeneration() {
        linesToPrintAtEndOfGeneration.forEach(writer::println);
    }

    public void addLineToPrintAtEndOfGeneration(String line) {
        linesToPrintAtEndOfGeneration.add(line);
    }
    public void fieldFixedToValue(Field field, Object current) {}
    public void unableToStepFurther(ReductiveState reductiveState) {}
    public void noValuesForField(ReductiveState reductiveState, Field field) {}
    public void unableToEmitRowAsSomeFieldSpecsAreEmpty(ReductiveState reductiveState, Map<Field, FieldSpec> fieldSpecsPerField) {}
}