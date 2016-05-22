package org.rayn.jflask.framework.mvc;

import org.rayn.jflask.framework.core.ClassScanner;
import org.rayn.jflask.framework.core.impl.DefaultClassScanner;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ControllerHelper
 * Created by Raynxxx on 2016/05/13.
 */
public class ControllerHelper {

    private static final Map<Request, RouteHandler> controllerMap = new LinkedHashMap<Request, RouteHandler>();

    static {
    }

    public static Map<Request, RouteHandler> getControllerMap() {
        return controllerMap;
    }
}
