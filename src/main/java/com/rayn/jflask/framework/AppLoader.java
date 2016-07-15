package com.rayn.jflask.framework;

import com.rayn.jflask.framework.core.ConfigHelper;
import com.rayn.jflask.framework.ioc.IOCBuilder;
import com.rayn.jflask.framework.routing.RouteLoader;
import com.rayn.jflask.framework.util.ClassUtil;

/**
 * AppLoader
 * Created by Raynxxx on 2016/05/23.
 */
public final class AppLoader {

    public static void init() {
        Class<?>[] loadWhenAppInit = {
                ConfigHelper.class,
                RouteLoader.class,
                IOCBuilder.class
        };

        for (Class<?> cls : loadWhenAppInit) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
