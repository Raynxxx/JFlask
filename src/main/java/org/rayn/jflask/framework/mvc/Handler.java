package org.rayn.jflask.framework.mvc;

import java.lang.reflect.Method;

/**
 * Handler
 * Created by Raynxxx on 2016/05/14.
 */
public class Handler {
    private Class<?> controllerClass;
    private Method routeMethod;

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
}
