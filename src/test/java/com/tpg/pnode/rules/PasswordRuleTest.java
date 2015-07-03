package com.tpg.pnode.rules;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link com.tpg.pnode.rules.PasswordRule}
 */
public class PasswordRuleTest {

    @Test
    public void doesNotPassOnEmptyInput() {
        PasswordRule p = new PasswordRule();
        boolean wouldFire = p.evaluate();

        assertFalse(wouldFire);
    }


    @Test
    public void doesNotFireOnWrongPassword() {
        PasswordRule p = new PasswordRule();
        p.setInput("little pig, let me in!");
        boolean wouldFire = p.evaluate();

        assertFalse(wouldFire);
    }


    @Test
    public void firesOnGoodPassword() {
        PasswordRule p = new PasswordRule();
        p.setInput("peek-a-boo");
        boolean wouldFire = p.evaluate();

        assertTrue(wouldFire);
    }

}
