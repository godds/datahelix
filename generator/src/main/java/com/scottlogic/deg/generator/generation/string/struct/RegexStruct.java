package com.scottlogic.deg.generator.generation.string.struct;

import dk.brics.automaton.Automaton;

import java.util.Objects;
import java.util.function.Function;

public class RegexStruct  {

    private final Automaton automaton;

    private final String representation;

    public RegexStruct(Automaton automaton, String representation) {
        this.automaton = automaton;
        this.representation = representation;
    }

    public Automaton implementation() {
        return automaton;
    }

    public String representation() {
        return representation;
    }

    public RegexStruct union(RegexStruct other) {
        return combine(other, automaton::union, "∪");
    }

    public RegexStruct intersect(RegexStruct other) {
        return combine(other, automaton::intersection, "∩");
    }

    private RegexStruct combine(final RegexStruct other,
                                final Function<Automaton, Automaton> automatonMergeFunction,
                                final String joinSymbol) {
        final Automaton mergedAutomaton = genericAutomaton(automatonMergeFunction, other.implementation());
        final String mergedRepresentation = genericRepresentation(
            joinSymbol,
            representation,
            other.representation());
        return new RegexStruct(mergedAutomaton, mergedRepresentation);
    }

    private static Automaton genericAutomaton(Function<Automaton, Automaton> function, Automaton other) {
        return function.apply(other);
    }

    private static String genericRepresentation(String joiner, String left, String right) {
        return String.format("(%s %s %s)", left, joiner, right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegexStruct that = (RegexStruct) o;
        return Objects.equals(automaton, that.automaton);
    }

    @Override
    public int hashCode() {
        return Objects.hash(automaton);
    }

    @Override
    public String toString() {
        return "RegexStruct{" +
            "representation='" + representation + '\'' +
            '}';
    }
}
