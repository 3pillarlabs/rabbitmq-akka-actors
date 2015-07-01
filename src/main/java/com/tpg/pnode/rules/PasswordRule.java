package com.tpg.pnode.rules;

import org.easyrules.core.BasicRule;

public class PasswordRule extends BasicRule {

    private String input;


    @Override
    public String getName() {
        return "password match";
    }


    @Override
    public boolean evaluate() {
        return "peek-a-boo".equals(input);
    }


    @Override
    public void execute() throws Exception {
        System.out.println("you found the password!");
    }


    public void setInput(String input) {
        this.input = input;
    }


}
