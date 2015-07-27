package com.tpg.pnode.rules;


import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;

import java.util.HashSet;
import java.util.Set;


public final class RuleSetBuilder {

    private final Set<Rule> rules = new HashSet<>();

    public RuleSetBuilder() {
        // nothing
    }

    public RuleSetBuilder add(final Rule rule) {
        rules.add(rule);

        return this;
    }

    public RuleSet build() {
        final RulesEngine re = RulesEngineBuilder.aNewRulesEngine().build();
        rules.forEach(rule -> re.registerRule(rule));

        return new RuleSet(re, rules);
    }


}
