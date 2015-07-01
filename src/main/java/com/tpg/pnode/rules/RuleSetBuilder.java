package com.tpg.pnode.rules;


import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;


public final class RuleSetBuilder {

    private RuleSetBuilder() {
        // nothing
    }

    public static RuleSet build() {
        final PasswordRule pr = new PasswordRule();
        final RulesEngine re = RulesEngineBuilder.aNewRulesEngine().build();
        re.registerRule(pr);

        return new RuleSet(re, pr);
    }


}
