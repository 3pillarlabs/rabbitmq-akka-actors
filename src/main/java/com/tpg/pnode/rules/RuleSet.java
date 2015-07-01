package com.tpg.pnode.rules;


import org.easyrules.api.RulesEngine;


public final class RuleSet {

    private final RulesEngine rulesEngine;
    private final PasswordRule passwordRule;


    public RuleSet(RulesEngine rulesEngine, PasswordRule passwordRule) {
        this.rulesEngine = rulesEngine;
        this.passwordRule = passwordRule;
    }


    public RulesEngine getRulesEngine() {
        return rulesEngine;
    }


    public PasswordRule getPasswordRule() {
        return passwordRule;
    }
}
