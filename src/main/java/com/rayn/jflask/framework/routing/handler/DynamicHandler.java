package com.rayn.jflask.framework.routing.handler;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * DynamicHandler
 * Created by Raynxxx on 2016/07/12.
 */
public class DynamicHandler implements Serializable, Handler {

    private final Class<?> controller;
    private final Method routeMethod;
    private Matcher pathMatcher;
    private Map<String, Integer> namedGroups;

    public DynamicHandler(Class<?> controller, Method routeMethod) {
        this.controller = controller;
        this.routeMethod = routeMethod;
    }

    @Override
    public Class<?> getController() {
        return controller;
    }

    @Override
    public Method getRouteMethod() {
        return routeMethod;
    }

    public Matcher getPathMatcher() {
        return pathMatcher;
    }

    public Map<String, Integer> getNamedGroups() {
        return namedGroups;
    }

    public void setPathMatcher(Matcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    public void setNamedGroups(Map<String, Integer> namedGroups) {
        this.namedGroups = namedGroups;
    }

    @Override
    public String toString() {
        return String.format("DynamicHandler %s#%s", this.controller.getName(), this.routeMethod.getName());
    }
}
