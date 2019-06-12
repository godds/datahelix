package com.scottlogic.deg.generator.decisiontree.FieldSpecTree;

import com.scottlogic.deg.common.profile.Field;
import com.scottlogic.deg.generator.fieldspecs.FieldSpec;

import java.util.Collection;
import java.util.Map;

public class FSConstraintNode {
    private final Map<Field, FieldSpec> fieldSpecs;
    private final Collection<FSDecisionNode> decisions;

    public FSConstraintNode(Map<Field, FieldSpec> fieldSpecs, Collection<FSDecisionNode> decisions) {
        this.fieldSpecs = fieldSpecs;
        this.decisions = decisions;
    }

    public Collection<FSDecisionNode> getDecisions(){
        return decisions;
    }

    public Map<Field, FieldSpec> getFieldSpecs() {
        return fieldSpecs;
    }

    @Override
    public String toString() {
        return fieldSpecs.toString() + " " + decisions.size() + " decisions";
    }
}
