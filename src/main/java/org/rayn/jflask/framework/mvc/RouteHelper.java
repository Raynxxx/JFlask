package org.rayn.jflask.framework.mvc;

import org.rayn.jflask.framework.InstanceFactory;
import org.rayn.jflask.framework.core.ClassScanner;
import org.rayn.jflask.framework.core.ConfigHelper;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * RouteHelper
 * Created by Raynxxx on 2016/05/13.
 */
public class RouteHelper {

    private static final Map<Request, Handler> routeMap = new LinkedHashMap<Request, Handler>();
    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();
    private static final String basePackage = ConfigHelper.getString("app.base_package", "");

    static {
        List<Class<?>> routeList = classScanner.getClassList(basePackage);

    }

    public static Map<Request, Handler> getRouteMap() {
        return routeMap;
    }
}
