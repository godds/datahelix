package com.scottlogic.deg.generator.generation.string.struct;

import dk.brics.automaton.Automaton;

public class RegexStruct {

    private final Automaton automaton;

    private final String representation;

    public RegexStruct(Automaton automaton, String representation) {
        this.automaton = automaton;
        this.representation = representation;
    }

    public Automaton automaton() {
        return automaton;
    }

    public String representation() {
        return representation;
    }
}
