package com.tpg.java.rules;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link RuleSetBuilder}
 */
public class RuleSetBuilderTest {

    @Test
    public void canAddRules() {
        RuleSetBuilder rb = new RuleSetBuilder();
        RuleSet rs = rb.add(new PasswordRule()).build();

        assertEquals(rs.getRules().size(), 1);
    }
}
