package com.rayn.jflask.framework.routing.handler;

import java.lang.reflect.Method;

/**
 * Handler
 * Created by Raynxxx on 2016/07/13.
 */
public interface Handler {

    Class<?> getController();

    Method getRouteMethod();
}
