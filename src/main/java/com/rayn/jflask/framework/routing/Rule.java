package com.rayn.jflask.framework.routing;

/**
 * Rule
 * Created by Raynxxx on 2016/07/13.
 */
public class Rule {
    private String staticPart;
    private String variable;

    public Rule(String staticPart, String variable) {
        this.staticPart = staticPart;
        this.variable = variable;
    }

    public String getStaticPart() {
        return staticPart;
    }

    public String getVariable() {
        return variable;
    }
}