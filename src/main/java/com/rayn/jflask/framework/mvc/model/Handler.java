package com.rayn.jflask.framework.mvc.model;

import java.lang.reflect.Method;
import java.util.regex.Matcher;

/**
 * Handler
 * Created by Raynxxx on 2016/05/14.
 */
public class Handler {
    private Class<?> controllerClass;
    private Method routeMethod;
    private Matcher pathMatcher;

    public Handler(Class<?> controllerClass, Method routeMethod) {
        this.controllerClass = controllerClass;
        this.routeMethod = routeMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getRouteMethod() {
        return routeMethod;
    }

    public Matcher getPathMatcher() {
        return pathMatcher;
    }

    public void setPathMatcher(Matcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }
}
