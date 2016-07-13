package com.rayn.jflask.framework.routing.converter;

/**
 * PathRuleConverter
 * Created by Raynxxx on 2016/07/12.
 */
public class PathRuleConverter implements RuleConverter {
    @Override
    public String getPattern() {
        return ".+?";
    }
}
