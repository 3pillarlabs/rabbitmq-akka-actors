package com.tpg.java.rules;

import org.easyrules.api.RulesEngine;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests for {@link RuleSet}
 */
public class RuleSetTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotSetNullRules() {
        new RuleSet(Mockito.mock(RulesEngine.class), null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void cannotSetNullRuleEngine() {
        new RuleSet(null, new HashSet<>());
    }


    @Test
    public void testSetAllInputs() {
        RulesEngine re = Mockito.mock(RulesEngine.class);
        Rule r1 = Mockito.spy(Rule.class);
        Rule r2 = Mockito.spy(Rule.class);

        Set<Rule> rules = new HashSet<>();
        rules.add(r1);
        rules.add(r2);

        RuleSet ruleSet = new RuleSet(re, rules);
        ruleSet.setInput("test");

        Mockito.verify(r1).setInput("test");
        Mockito.verify(r2).setInput("test");
    }
}
