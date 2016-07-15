package com.rayn.jflask.framework.routing.handler;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * StaticHandler
 * Created by Raynxxx on 2016/05/14.
 */
public class StaticHandler implements Serializable, Handler {

    private final Class<?> controller;
    private final Method routeMethod;

    public StaticHandler(Class<?> controller, Method routeMethod) {
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

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return String.format("StaticHandler %s#%s", this.controller.getName(), this.routeMethod.getName());
    }
}
