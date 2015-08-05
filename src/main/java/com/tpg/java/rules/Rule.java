package com.tpg.java.rules;

/**
 * Represents a rule in our system
 */
public interface Rule {

    /**
     * Use this to set the input for the rule - every time the input changes
     */
    public void setInput(String input);

    /**
     * @return the input that the rule currently operates on
     */
    public String getInput();

}
