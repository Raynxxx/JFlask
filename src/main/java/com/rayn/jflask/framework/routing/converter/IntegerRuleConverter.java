package com.rayn.jflask.framework.routing.converter;

/**
 * IntegerRuleConverter
 * Created by Raynxxx on 2016/07/12.
 */
public class IntegerRuleConverter implements RuleConverter {
    @Override
    public String getPattern() {
        return "\\d+";
    }
}
