package com.rayn.jflask.framework.routing.converter;

/**
 * RuleConverter
 * Created by Raynxxx on 2016/07/12.
 */
public class DefaultRuleConverter implements RuleConverter {

    @Override
    public String getPattern() {
        return "[^/]+";
    }
}
