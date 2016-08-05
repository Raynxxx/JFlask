package com.rayn.jflask.framework;

import com.rayn.jflask.framework.core.ConfigHelper;
import com.rayn.jflask.framework.ioc.IocInitializer;
import com.rayn.jflask.framework.orm.OrmInitializer;
import com.rayn.jflask.framework.routing.RouteInitializer;
import com.rayn.jflask.framework.util.ClassUtil;

/**
 * ComponentLoader
 * Created by Raynxxx on 2016/05/23.
 */
public final class ComponentLoader {

    public static void init() {
        Class<?>[] loadWhenAppInit = {
                ConfigHelper.class,
                RouteInitializer.class,
                OrmInitializer.class,
                IocInitializer.class
        };

        for (Class<?> cls : loadWhenAppInit) {
            ClassUtil.loadClass(cls.getName());
        }
    }

}
