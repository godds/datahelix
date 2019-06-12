//package com.scottlogic.deg.generator.decisiontree;
//
//import com.scottlogic.deg.common.profile.constraints.atomic.AtomicConstraint;
//import com.scottlogic.deg.common.profile.constraints.atomic.IsGreaterThanConstantConstraint;
//import com.scottlogic.deg.common.profile.constraints.atomic.IsInSetConstraint;
//import com.scottlogic.deg.common.profile.constraints.atomic.MatchesRegexConstraint;
//import com.scottlogic.deg.common.profile.Field;
//import com.scottlogic.deg.common.profile.Profile;
//import com.scottlogic.deg.common.profile.ProfileFields;
//import com.scottlogic.deg.common.profile.Rule;
//import com.scottlogic.deg.common.profile.constraints.Constraint;
//import com.scottlogic.deg.common.profile.constraints.grammatical.AndConstraint;
//import com.scottlogic.deg.common.profile.constraints.grammatical.ConditionalConstraint;
//import com.scottlogic.deg.common.profile.constraints.grammatical.NegatedGrammaticalConstraint;
//import com.scottlogic.deg.common.profile.constraints.grammatical.OrConstraint;
//import com.scottlogic.deg.common.profile.RuleInformation;
//import com.scottlogic.deg.generator.decisiontree.ConstraintNode;
//import com.scottlogic.deg.generator.decisiontree.FieldSpecTree.FSConstraintNode;
//import com.scottlogic.deg.generator.decisiontree.FieldSpecTree.FSDecisionTreeFactory;
//import com.scottlogic.deg.generator.decisiontree.testutils.*;
//import com.scottlogic.deg.generator.fieldspecs.FieldSpecFactory;
//import com.scottlogic.deg.generator.fieldspecs.FieldSpecMerger;
//import com.scottlogic.deg.generator.fieldspecs.RowSpecMerger;
//import com.scottlogic.deg.generator.reducer.ConstraintReducer;
//import com.scottlogic.deg.generator.restrictions.StringRestrictionsFactory;
//import org.hamcrest.core.Is;
//import org.hamcrest.core.IsNull;
//import org.junit.Assert;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.*;
//import java.util.regex.Pattern;
//
//import static org.hamcrest.CoreMatchers.*;
//import static org.hamcrest.Matchers.empty;
//import static com.shazam.shazamcrest.MatcherAssert.assertThat;
//import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
//
//class ProfileFSConstraintNodeFactoryTests {
//    private final Field fieldA = new Field("A");
//    private final Field fieldB = new Field("B");
//
//    private final List<Rule> rules = new ArrayList<>();
//    private FSConstraintNode actualOutput;
//    FSDecisionTreeFactory testObject = new FSDecisionTreeFactory(
//        new ConstraintReducer(
//            new FieldSpecFactory(
//                new StringRestrictionsFactory()),
//            new FieldSpecMerger()),
//        new RowSpecMerger(
//            new FieldSpecMerger()));
//
//    private void givenRule(Constraint... constraints) {
//        this.rules.add(new Rule(rule(""), Arrays.asList(constraints)));
//    }
//
//    @Test
//    void shouldReturnAnalysedProfileWithNoAnalysedRules_IfProfileHasNoRules() {
//        Profile testInput = new Profile(new ArrayList<>(), new ArrayList<>());
//
//        FSConstraintNode testOutput = testObject.create(testInput);
//
//        Assert.assertThat(testOutput, not(is(nullValue())));
//        Assert.assertThat(testOutput.getFieldSpecs().keySet().size(), is(0));
//        Assert.assertThat(testOutput, not(is(nullValue())));
//        Assert.assertThat(testOutput.getFieldSpecs().entrySet(), is(empty()));
//        Assert.assertThat(testOutput.getDecisions(), is(empty()));
//    }
//
//    @Test
//    void shouldReturnAnalysedProfileWithCorrectFields() {
//        List<Field> inputFieldList = Arrays.asList(new Field("one"), new Field("two"), new Field("three"));
//        Profile testInput = new Profile(inputFieldList, new ArrayList<>());
//
//        FSConstraintNode testOutput = testObject.create(testInput);
//        Set<Field> actualFields = testOutput.getFieldSpecs().keySet();
//
//        assertThat(actualFields, sameBeanAs(inputFieldList));
//    }
//
//    @Test
//    void shouldReturnAnalysedRuleWithNoDecisions_IfProfileContainsOnlyAtomicConstraints() {
//        List<Field> inputFieldList = Arrays.asList(new Field("one"), new Field("two"), new Field("three"));
//        IsInSetConstraint constraint0 = new IsInSetConstraint(inputFieldList.get(0), Collections.singleton(10), rules());
//        IsGreaterThanConstantConstraint constraint1 = new IsGreaterThanConstantConstraint(inputFieldList.get(0), 0, rules());
//        MatchesRegexConstraint constraint2 = new MatchesRegexConstraint(inputFieldList.get(1), Pattern.compile("start.*end"), rules());
//        Rule testRule = new Rule(rule("test"), Arrays.asList(constraint0, constraint1, constraint2));
//        Profile testInput = new Profile(inputFieldList, Collections.singletonList(testRule));
//
//
//        FSConstraintNode outputRule = testObject.create(testInput);
//
//        Assert.assertThat("analyse() output is not null", outputRule, Is.is(IsNull.notNullValue()));
//        Assert.assertThat("Decision tree root has non-null list of decisions",
//            outputRule.getDecisions(), Is.is(IsNull.notNullValue()));
//        Assert.assertThat("Decision tree root has empty list of decisions",
//            outputRule.getDecisions().size(), Is.is(0));
//    }
//
//    @Test
//    void shouldReturnAnalysedRuleWithAllConstraintsInAtomicConstraintsCollection_IfProfileContainsOnlyAtomicConstraints() {
//        List<Field> inputFieldList = Arrays.asList(new Field("one"), new Field("two"), new Field("three"));
//        IsInSetConstraint constraint0 = new IsInSetConstraint(inputFieldList.get(0), Collections.singleton(10), rules());
//        IsGreaterThanConstantConstraint constraint1 = new IsGreaterThanConstantConstraint(inputFieldList.get(0), 0, rules());
//        MatchesRegexConstraint constraint2 = new MatchesRegexConstraint(inputFieldList.get(1), Pattern.compile("start.*end"), rules());
//        List<Constraint> inputConstraints = Arrays.asList(constraint0, constraint1, constraint2);
//        Rule testRule = new Rule(rule("test"), inputConstraints);
//        Profile testInput = new Profile(inputFieldList, Collections.singletonList(testRule));
//
//        FSConstraintNode outputRule = testObject.create(testInput);
//
//        Assert.assertThat("Decision tree root atomic constraint list is same size as original constraint list",
//            outputRule.getFieldSpecs().size(), Is.is(inputConstraints.size()));
//        for (Constraint constraint : inputConstraints) {
//            AtomicConstraint atomicConstraint = (AtomicConstraint) constraint;
//
////            Assert.assertThat("Each input constraint is in the decision tree root node atomic constraint list",
////                outputRule.getFieldSpecs(), hasItem(atomicConstraint));
//        }
//    }
//
//    @Test
//    void shouldReturnAnalysedRuleWithNoDecisions_IfProfileContainsOnlyAtomicConstraintsAndAndConstraints() {
//        List<Field> inputFieldList = Arrays.asList(new Field("one"), new Field("two"), new Field("three"));
//        IsInSetConstraint constraint0 = new IsInSetConstraint(inputFieldList.get(0), Collections.singleton(10), rules());
//        IsGreaterThanConstantConstraint constraint1 = new IsGreaterThanConstantConstraint(inputFieldList.get(0), 0, rules());
//        AndConstraint andConstraint0 = new AndConstraint(Arrays.asList(constraint0, constraint1));
//        MatchesRegexConstraint constraint2 = new MatchesRegexConstraint(inputFieldList.get(1), Pattern.compile("start.*end"), rules());
//        Rule testRule = new Rule(rule("test"), Arrays.asList(andConstraint0, constraint2));
//        Profile testInput = new Profile(inputFieldList, Collections.singletonList(testRule));
//
//        FSConstraintNode outputRule = testObject.create(testInput);
//
//        Assert.assertThat("analyse() output is not null", outputRule, Is.is(IsNull.notNullValue()));
//        Assert.assertThat("Decision tree root has non-null list of decisions",
//            outputRule.getDecisions(), Is.is(IsNull.notNullValue()));
//        Assert.assertThat("Decision tree root has empty list of decisions",
//            outputRule.getDecisions().size(), Is.is(0));
//    }
//
//    @Test
//    void shouldReturnAnalysedRuleWithAllAtomicConstraintsInAtomicConstraintsCollection_IfProfileContainsOnlyAtomicConstraintsAndAndConstraints() {
//        List<Field> inputFieldList = Arrays.asList(new Field("one"), new Field("two"), new Field("three"));
//        IsInSetConstraint constraint0 = new IsInSetConstraint(inputFieldList.get(0), Collections.singleton(10), rules());
//        IsGreaterThanConstantConstraint constraint1 = new IsGreaterThanConstantConstraint(inputFieldList.get(0), 0, rules());
//        AndConstraint andConstraint0 = new AndConstraint(Arrays.asList(constraint0, constraint1));
//        MatchesRegexConstraint constraint2 = new MatchesRegexConstraint(inputFieldList.get(1), Pattern.compile("start.*end"), rules());
//        Rule testRule = new Rule(rule("test"), Arrays.asList(andConstraint0, constraint2));
//        Profile testInput = new Profile(inputFieldList, Collections.singletonList(testRule));
//
//
//        FSConstraintNode outputRule = testObject.create(testInput);
//
//        Assert.assertThat("analyse() output is not null", outputRule, Is.is(IsNull.notNullValue()));
//        Assert.assertThat("Decision tree root contains correct number of atomic constraints",
//            outputRule.getFieldSpecs().size(), Is.is(3));
////        Assert.assertThat("Decision tree root atomic constraints list contains constraint 0",
////            outputRule.getFieldSpecs().contains(constraint0), Is.is(true));
////        Assert.assertThat("Decision tree root atomic constraints list contains constraint 1",
////            outputRule.getFieldSpecs().contains(constraint1), Is.is(true));
////        Assert.assertThat("Decision tree root atomic constraints list contains constraint 2",
////            outputRule.getFieldSpecs().contains(constraint2), Is.is(true));
//    }
//
//    @Test
//    void shouldReturnAnalysedRuleWithDecisionForEachOrConstraint() {
//        List<Field> inputFieldList = Arrays.asList(new Field("one"), new Field("two"), new Field("three"));
//        IsInSetConstraint constraint0 = new IsInSetConstraint(inputFieldList.get(0), Collections.singleton(10), rules());
//        IsGreaterThanConstantConstraint constraint1 = new IsGreaterThanConstantConstraint(inputFieldList.get(0), 0, rules());
//        OrConstraint orConstraint0 = new OrConstraint(Arrays.asList(constraint0, constraint1));
//        IsInSetConstraint constraint2 = new IsInSetConstraint(inputFieldList.get(1), Collections.singleton("steam"), rules());
//        IsInSetConstraint constraint3 = new IsInSetConstraint(inputFieldList.get(1), Collections.singleton("diesel"), rules());
//        OrConstraint orConstraint1 = new OrConstraint(Arrays.asList(constraint2, constraint3));
//        Rule testRule = new Rule(rule("test"), Arrays.asList(orConstraint0, orConstraint1));
//        Profile testInput = new Profile(inputFieldList, Collections.singletonList(testRule));
//
//        FSConstraintNode outputRule = testObject.create(testInput);
//
//        Assert.assertThat("analyse() output is not null", outputRule, Is.is(IsNull.notNullValue()));
//        Assert.assertThat("Decision tree root contains correct number of decisions",
//            outputRule.getDecisions().size(), Is.is(2));
//    }
//
//    // checks (A OR B) AND (C OR D)
//    @Test
//    void shouldReturnAnalysedRuleWithNoAtomicConstraints_IfAllAtomicConstraintsInProfileAreChildrenOfOrConstraints() {
//        List<Field> inputFieldList = Arrays.asList(new Field("one"), new Field("two"), new Field("three"));
//        IsInSetConstraint constraintA = new IsInSetConstraint(inputFieldList.get(0), Collections.singleton(10), rules());
//        IsGreaterThanConstantConstraint constraintB = new IsGreaterThanConstantConstraint(inputFieldList.get(0), 0, rules());
//        OrConstraint orConstraint0 = new OrConstraint(Arrays.asList(constraintA, constraintB));
//        IsInSetConstraint constraintC = new IsInSetConstraint(inputFieldList.get(1), Collections.singleton("steam"), rules());
//        IsInSetConstraint constraintD = new IsInSetConstraint(inputFieldList.get(1), Collections.singleton("diesel"), rules());
//        OrConstraint orConstraint1 = new OrConstraint(Arrays.asList(constraintC, constraintD));
//        Rule testRule = new Rule(rule("test"), Arrays.asList(orConstraint0, orConstraint1));
//        Profile testInput = new Profile(inputFieldList, Collections.singletonList(testRule));
//
//        FSConstraintNode outputRule = testObject.create(testInput);
//
//        Assert.assertThat("analyse() output is not null", outputRule, Is.is(IsNull.notNullValue()));
//        Assert.assertThat("Decision tree root contains no atomic constraints",
//            outputRule.getFieldSpecs().size(), Is.is(0));
//    }
//
//    // checks (A OR B) AND (C OR D)
//    @Test
//    void shouldReturnAnalysedRuleWithCorrectDecisionStructure_IfAllAtomicConstraintsInProfileAreChildrenOfOrConstraints() {
//        List<Field> inputFieldList = Arrays.asList(new Field("one"), new Field("two"), new Field("three"));
//        IsInSetConstraint constraintA = new IsInSetConstraint(inputFieldList.get(0), Collections.singleton(10), rules());
//        IsGreaterThanConstantConstraint constraintB = new IsGreaterThanConstantConstraint(inputFieldList.get(0), 0, rules());
//        OrConstraint orConstraint0 = new OrConstraint(Arrays.asList(constraintA, constraintB));
//        IsInSetConstraint constraintC = new IsInSetConstraint(inputFieldList.get(1), Collections.singleton("steam"), rules());
//        IsInSetConstraint constraintD = new IsInSetConstraint(inputFieldList.get(1), Collections.singleton("diesel"), rules());
//        OrConstraint orConstraint1 = new OrConstraint(Arrays.asList(constraintC, constraintD));
//        Rule testRule = new Rule(rule("test"), Arrays.asList(orConstraint0, orConstraint1));
//        Profile testInput = new Profile(inputFieldList, Collections.singletonList(testRule));
//
//        FSConstraintNode outputRule = testObject.create(testInput);
//
//        Assert.assertThat("analyse() output is not null", outputRule, Is.is(IsNull.notNullValue()));
//        Assert.assertTrue(isEquivalentTo(
//            new ConstraintNode(
//                Collections.emptySet(),
//                Arrays.asList(
//                    new DecisionNode(
//                        new ConstraintNode(constraintA),
//                        new ConstraintNode(constraintB)
//                    ),
//                    new DecisionNode(
//                        new ConstraintNode(constraintC),
//                        new ConstraintNode(constraintD)
//                    )
//                )
//            ),
//            outputRule.getRootNode())
//        );
//    }
//
//    // Checks (A OR (B AND C)) AND (D OR E)
//    @Test
//    void shouldReturnAnalysedRuleWithCorrectDecisionStructure_IfAllAtomicConstraintsInProfileAreChildrenOfOrAndAndConstraints() {
//        List<Field> inputFieldList = Arrays.asList(new Field("one"), new Field("two"), new Field("three"));
//        IsInSetConstraint constraintA = new IsInSetConstraint(inputFieldList.get(0), Collections.singleton(10), rules());
//        IsGreaterThanConstantConstraint constraintB = new IsGreaterThanConstantConstraint(inputFieldList.get(0), 0, rules());
//        IsGreaterThanConstantConstraint constraintC = new IsGreaterThanConstantConstraint(inputFieldList.get(0), 5, rules());
//        AndConstraint andConstraint0 = new AndConstraint(Arrays.asList(constraintC, constraintB));
//        OrConstraint orConstraint0 = new OrConstraint(Arrays.asList(constraintA, andConstraint0));
//        IsInSetConstraint constraintD = new IsInSetConstraint(inputFieldList.get(1), Collections.singleton("steam"), rules());
//        IsInSetConstraint constraintE = new IsInSetConstraint(inputFieldList.get(1), Collections.singleton("diesel"), rules());
//        OrConstraint orConstraint1 = new OrConstraint(Arrays.asList(constraintD, constraintE));
//        Rule testRule = new Rule(rule("test"), Arrays.asList(orConstraint0, orConstraint1));
//        Profile testInput = new Profile(inputFieldList, Collections.singletonList(testRule));
//
//        FSConstraintNode outputRule = testObject.create(testInput);
//
//        Assert.assertThat("analyse() output is not null", outputRule, Is.is(IsNull.notNullValue()));
//        Assert.assertTrue(isEquivalentTo(
//            new ConstraintNode(
//                Collections.emptySet(),
//                Arrays.asList(
//                    new DecisionNode(
//                        new ConstraintNode(
//                            constraintA
//                        ),
//                        new ConstraintNode(
//                            constraintC,
//                            constraintB
//                        )
//                    ),
//                    new DecisionNode(
//                        new ConstraintNode(constraintD),
//                        new ConstraintNode(constraintE)
//                    )
//                )
//            ),
//            outputRule.getRootNode())
//        );
//    }
//
//    // Checks IF (A) THEN B ELSE C
//    @Test
//    void shouldReturnAnalysedRuleWithCorrectDecisionStructure_IfConditionalConstraintIsPresent() {
//        List<Field> inputFieldList = Arrays.asList(new Field("one"), new Field("two"), new Field("three"));
//        IsInSetConstraint constraintA = new IsInSetConstraint(inputFieldList.get(0), Collections.singleton(10), rules());
//        IsGreaterThanConstantConstraint constraintB = new IsGreaterThanConstantConstraint(inputFieldList.get(1), 10, rules());
//        IsGreaterThanConstantConstraint constraintC = new IsGreaterThanConstantConstraint(inputFieldList.get(1), 20, rules());
//        ConditionalConstraint conditionalConstraint = new ConditionalConstraint(constraintA, constraintB, constraintC);
//        Rule testRule = new Rule(rule("test"), Collections.singletonList(conditionalConstraint));
//        Profile testInput = new Profile(inputFieldList, Collections.singletonList(testRule));
//
//        FSConstraintNode outputRule = testObject.create(testInput);
//
//        Assert.assertThat("analyse() output is not null", outputRule, Is.is(IsNull.notNullValue()));
//        Assert.assertTrue(isEquivalentTo(
//            new ConstraintNode(
//                Collections.emptySet(),
//                Collections.singletonList(
//                    new DecisionNode(
//                        new ConstraintNode(
//                            Arrays.asList(
//                                constraintA,
//                                constraintB),
//                            Collections.emptySet()
//                        ),
//                        new ConstraintNode(
//                            Arrays.asList(
//                                constraintA.negate(),
//                                constraintC
//                            ),
//                            Collections.emptySet()
//                        )
//                    )
//                )
//            ),
//            outputRule.getRootNode())
//        );
//    }
//
//    // Checks IF (A OR B) THEN C
//    @Test
//    void shouldReturnAnalysedRuleWithCorrectDecisionStructure_IfConditionalConstraintWithNestedOrIsPresent() {
//        AtomicConstraint aEquals10 = new IsInSetConstraint(fieldA, Collections.singleton(10), rules());
//        AtomicConstraint aGreaterThan10 = new IsGreaterThanConstantConstraint(fieldA, 10, rules());
//        AtomicConstraint bGreaterThan20 = new IsGreaterThanConstantConstraint(fieldB, 20, rules());
//
//        givenRule(
//            new ConditionalConstraint(
//                aEquals10.or(aGreaterThan10),
//                bGreaterThan20));
//
//        Assert.assertTrue(
//            isEquivalentTo(
//                getResultingRootOption(), new ConstraintNode(
//                    Collections.emptyList(),
//                    Collections.singletonList(
//                        new DecisionNode(
//                            /* OPTION 1: AND(C, OR(A, B))  */
//                            new ConstraintNode(
//                                Collections.singletonList(bGreaterThan20),
//                                Collections.singleton(
//                                    new DecisionNode(
//                                        new ConstraintNode(aEquals10),
//                                        new ConstraintNode(aGreaterThan10)))),
//                            /* OPTION 2: AND(¬A, ¬B)  */
//                            new ConstraintNode(
//                                aEquals10.negate(),
//                                aGreaterThan10.negate()
//                            )
//                        )
//                    )
//                )
//            )
//        );
//    }
//
//    // NOT (IF A THEN B ELSE C) - edge case
//    @Test
//    void shouldReturnAnalysedRuleWithCorrectDecisionStructure_IfNegatedConditionalConstraintIsPresent() {
//        List<Field> inputFieldList = Arrays.asList(new Field("one"), new Field("two"), new Field("three"));
//        IsInSetConstraint constraintA = new IsInSetConstraint(inputFieldList.get(0), Collections.singleton(10), rules());
//        IsGreaterThanConstantConstraint constraintB = new IsGreaterThanConstantConstraint(inputFieldList.get(1), 20, rules());
//        IsGreaterThanConstantConstraint constraintC = new IsGreaterThanConstantConstraint(inputFieldList.get(1), 10, rules());
//        ConditionalConstraint conditionalConstraint = new ConditionalConstraint(constraintA, constraintB, constraintC);
//        Constraint notConstraint = conditionalConstraint.negate();
//        Rule testRule = new Rule(rule("test"), Collections.singletonList(notConstraint));
//        Profile testInput = new Profile(inputFieldList, Collections.singletonList(testRule));
//
//        FSConstraintNode outputRule = testObject.create(testInput);
//
//        Assert.assertThat("analyse() output is not null", outputRule, Is.is(IsNull.notNullValue()));
//        Assert.assertTrue(isEquivalentTo(
//            new ConstraintNode(
//                Collections.emptySet(),
//                Collections.singletonList(
//                    new DecisionNode(
//                        new ConstraintNode(
//                            Arrays.asList(
//                                constraintA,
//                                constraintB.negate()
//                            ),
//                            Collections.emptySet()
//                        ),
//                        new ConstraintNode(
//                            Arrays.asList(
//                                constraintA.negate(),
//                                constraintC.negate()
//                            ),
//                            Collections.emptySet()
//                        )
//                    )
//                )
//            ),
//            outputRule.getRootNode())
//        );
//    }
//
//    // NOT (IF A THEN B) - other edge case
//    @Test
//    void shouldReturnAnalysedRuleWithCorrectDecisionStructure_IfNegatedConditionalConstraintWithoutElseClauseIsPresent() {
//        // ¬(A -> B)
//        // is equivalent to
//        // A ^ ¬B
//
//        AtomicConstraint aEqualTo10 = new IsInSetConstraint(fieldA, Collections.singleton(10), rules());
//        AtomicConstraint bGreaterThan20 = new IsGreaterThanConstantConstraint(fieldB, 20, rules());
//
//        Constraint inputRule = new ConditionalConstraint(aEqualTo10, bGreaterThan20).negate();
//
//        ConstraintNode expectedOutput = new ConstraintNode(
//            aEqualTo10,
//            bGreaterThan20.negate());
//
//        givenRule(inputRule);
//
////        Assert.assertTrue(
////            isEquivalentTo(
////                getResultingRootOption(), expectedOutput
////            )
////        );
//    }
//
//    // NOT (NOT A)
//    @Test
//    void shouldReturnAnalysedRuleWithCorrectDecisionStructure_IfDoubleNegationIsPresent() {
//        List<Field> inputFieldList = Arrays.asList(new Field("one"), new Field("two"), new Field("three"));
//        IsInSetConstraint constraintA = new IsInSetConstraint(inputFieldList.get(0), Collections.singleton(10), rules());
//        Constraint notConstraint0 = constraintA.negate();
//        Constraint notConstraint1 = notConstraint0.negate();
//        Rule testRule = new Rule(rule("test"), Collections.singletonList(notConstraint1));
//        Profile testInput = new Profile(inputFieldList, Collections.singletonList(testRule));
//
//        FSConstraintNode outputRule = testObject.create(testInput);
//
//        Assert.assertThat("analyse() output is not null", outputRule, Is.is(IsNull.notNullValue()));
//        // Result should just be A.
//        Assert.assertThat("Decision tree root contains one atomic constraint",
//            outputRule.getFieldSpecs().size(), Is.is(1));
//        Assert.assertThat("Decision tree root contains no decisions",
//            outputRule.getDecisions().size(), Is.is(0));
////        Assert.assertThat("Atomic constraint of decision tree root is constraint A",
////            outputRule.getFieldSpecs().contains(constraintA), Is.is(true));
//    }
//
//    // NOT (A AND B)
//    @Test
//    void shouldReturnAnalysedRuleWithCorrectDecisionStructure_IfNegatedAndIsPresent() {
//        List<Field> inputFieldList = Arrays.asList(new Field("one"), new Field("two"), new Field("three"));
//        IsInSetConstraint constraintA = new IsInSetConstraint(inputFieldList.get(0), Collections.singleton(10), rules());
//        IsGreaterThanConstantConstraint constraintB = new IsGreaterThanConstantConstraint(inputFieldList.get(1), 5, rules());
//        NegatedGrammaticalConstraint notConstraint = (NegatedGrammaticalConstraint) new AndConstraint(Arrays.asList(constraintA, constraintB)).negate();
//        Rule testRule = new Rule(rule("test"), Collections.singletonList(notConstraint));
//        Profile testInput = new Profile(inputFieldList, Collections.singletonList(testRule));
//
//        FSConstraintNode outputRule = testObject.create(testInput);
//
//        Assert.assertThat("analyse() output is not null", outputRule, Is.is(IsNull.notNullValue()));
//        // Result should be (NOT A) OR (NOT B)
//        Assert.assertTrue(isEquivalentTo(
//            new ConstraintNode(
//                Collections.emptySet(),
//                Collections.singletonList(
//                    new DecisionNode(
//                        new ConstraintNode(
//                            Collections.singletonList(constraintA.negate()),
//                            Collections.emptySet()
//                        ),
//                        new ConstraintNode(
//                            Collections.singletonList(constraintB.negate()),
//                            Collections.emptySet()
//                        )
//                    )
//                )
//            ),
//            outputRule.getRootNode())
//        );
//    }
//
//    // (A OR B) OR C
//    @Test
//    void shouldReturnAnalysedRuleWithCorrectDecisionStructure_IfNestedOrsArePresent() {
//        AtomicConstraint constraintA = new IsInSetConstraint(fieldA, Collections.singleton(10), rules());
//        AtomicConstraint constraintB = new IsGreaterThanConstantConstraint(fieldB, 20, rules());
//        AtomicConstraint constraintC = new IsGreaterThanConstantConstraint(fieldB, 10, rules());
//
//        givenRule(
//            new OrConstraint(
//                new OrConstraint(constraintA, constraintB),
//                constraintC));
//
//        Assert.assertTrue(
//            isEquivalentTo(
//                getResultingRootOption(),
//                new ConstraintNode(
//                    Collections.emptyList(),
//                    Collections.singletonList(
//                        new DecisionNode(
//                            new ConstraintNode(constraintA),
//                            new ConstraintNode(constraintB),
//                            new ConstraintNode(constraintC))
//                    )
//                )
//            )
//        );
//    }
//
//    private boolean isEquivalentTo(ConstraintNode expected, ConstraintNode actual) {
//        TreeComparisonContext context = new TreeComparisonContext();
//        AnyOrderCollectionEqualityComparer defaultAnyOrderCollectionEqualityComparer = new AnyOrderCollectionEqualityComparer();
//        ConstraintNodeComparer constraintNodeComparer = new ConstraintNodeComparer(
//            context,
//            defaultAnyOrderCollectionEqualityComparer,
//            new DecisionComparer(),
//            defaultAnyOrderCollectionEqualityComparer,
//            new AnyOrderCollectionEqualityComparer(new DecisionComparer()));
//
//        boolean match = constraintNodeComparer.equals(expected, actual);
//        if (!match){
//            new TreeComparisonReporter().reportMessages(context);
//        }
//
//        return match;
//    }
//
//    private static Set<RuleInformation> rules(){
//        return Collections.singleton(rule("rules"));
//    }
//
//    private static RuleInformation rule(String description){
//        return new RuleInformation(description);
//    }
//}
