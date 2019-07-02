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

package com.scottlogic.deg.generator.validators;

import com.google.inject.Inject;
import com.scottlogic.deg.common.profile.Field;
import com.scottlogic.deg.common.profile.ProfileFields;
import com.scottlogic.deg.generator.decisiontree.*;
import com.scottlogic.deg.generator.fieldspecs.FieldSpec;
import com.scottlogic.deg.generator.fieldspecs.RowSpec;
import com.scottlogic.deg.generator.fieldspecs.RowSpecMerger;
import com.scottlogic.deg.generator.reducer.ConstraintReducer;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ContradictionDecisionTreeValidator {
    private final RowSpecMerger rowSpecMerger;
    private final ConstraintReducer constraintReducer;

    @Inject
    public ContradictionDecisionTreeValidator(RowSpecMerger rowSpecMerger, ConstraintReducer constraintReducer){
        this.rowSpecMerger = rowSpecMerger;
        this.constraintReducer = constraintReducer;
    }

    public DecisionTree markContradictions(DecisionTree tree) {
        return new DecisionTree(markContradictions(tree.getRootNode(), tree.getFields()), tree.getFields());
    }

    private ConstraintNode markContradictions(ConstraintNode node, ProfileFields profileFields){
        return markContradictions(node, getIdentityRowSpec(profileFields), profileFields);
    }

    private ConstraintNode markContradictions(ConstraintNode node, RowSpec accumulatedSpec, ProfileFields profileFields){
        final Optional<RowSpec> nominalRowSpec = node.getOrCreateRowSpec(() -> constraintReducer.reduceConstraintsToRowSpec(
            profileFields,
            node.getAtomicConstraints()
        ));

        if (!nominalRowSpec.isPresent()) {
            return node.markNode(NodeMarking.CONTRADICTORY);
        }

        final Optional<RowSpec> mergedRowSpecOpt = rowSpecMerger.merge(
            Arrays.asList(
                nominalRowSpec.get(),
                accumulatedSpec
            )
        );

        if (!mergedRowSpecOpt.isPresent()) {
            return node.markNode(NodeMarking.CONTRADICTORY);
        }

        if (node.getDecisions().isEmpty()) {
            return node;
        } else {
            Collection<DecisionNode> decisions = node.getDecisions()
                .stream()
                .map(d -> markContradictions(d, mergedRowSpecOpt.get(), profileFields))
                .collect(Collectors.toList());
            boolean nodeIsContradictory = decisions.stream().allMatch(this::isNodeContradictory);
            ConstraintNode transformed = node.setDecisions(decisions);
            return nodeIsContradictory ? transformed.markNode(NodeMarking.CONTRADICTORY) : transformed;
        }
    }

    private DecisionNode markContradictions(DecisionNode node, RowSpec accumulatedSpec, ProfileFields profileFields){
        if (node.getOptions().isEmpty()){
            return node;
        }
        Collection<ConstraintNode> options = node.getOptions().stream()
            .map(c -> markContradictions(c, accumulatedSpec, profileFields))
            .collect(Collectors.toList());

        boolean decisionIsContradictory = options.stream().allMatch(this::isNodeContradictory);
        DecisionNode transformed = node.setOptions(options);
        if (decisionIsContradictory) {
            return transformed.markNode(NodeMarking.CONTRADICTORY);
        }
        return transformed;
    }

    private RowSpec getIdentityRowSpec(ProfileFields profileFields) {
        final Map<Field, FieldSpec> fieldToFieldSpec = profileFields.stream()
            .collect(Collectors.toMap(Function.identity(), field -> FieldSpec.Empty));

        return new RowSpec(profileFields, fieldToFieldSpec);
    }

    private boolean isNodeContradictory(Node node){
        return node.hasMarking(NodeMarking.CONTRADICTORY);
    }


}
