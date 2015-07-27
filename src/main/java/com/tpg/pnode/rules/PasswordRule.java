package com.tpg.pnode.rules;

import org.easyrules.core.BasicRule;

public class PasswordRule extends BasicRule implements Rule {

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


    @Override
    public void setInput(final String input) {
        this.input = input;
    }


    @Override
    public String getInput() {
        return input;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PasswordRule that = (PasswordRule) o;

        return !(input != null ? !input.equals(that.input) : that.input != null);

    }


    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (input != null ? input.hashCode() : 0);
        return result;
    }

}
