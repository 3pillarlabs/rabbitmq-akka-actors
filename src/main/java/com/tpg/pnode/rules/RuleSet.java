package com.tpg.pnode.rules;


import com.google.common.base.Preconditions;
import org.easyrules.api.RulesEngine;

import java.util.Set;


public final class RuleSet {

    private final RulesEngine rulesEngine;
    private final Set<Rule> rules;


    public RuleSet(final RulesEngine rulesEngine, final Set<Rule> rules) {
        Preconditions.checkArgument(rulesEngine != null, "rule engine cannot be null");
        Preconditions.checkArgument(rules != null, "rule set cannot be null");

        this.rulesEngine = rulesEngine;
        this.rules = rules;
    }


    /**
     * Convenience method to set the same input for all rules
     */
    public void setInput(final String input) {
        rules.forEach(rule -> rule.setInput(input));
    }


    public RulesEngine getRulesEngine() {
        return rulesEngine;
    }


    public Set<Rule> getRules() {
        return rules;
    }
}
