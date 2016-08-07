package com.rayn.jflask.framework;

import com.rayn.jflask.framework.aop.AopInitializer;
import com.rayn.jflask.framework.core.ClassHelper;
import com.rayn.jflask.framework.ioc.IocInitializer;
import com.rayn.jflask.framework.orm.ActiveRecordInitializer;
import com.rayn.jflask.framework.routing.RouterInitializer;
import com.rayn.jflask.framework.util.ClassUtil;

/**
 * ComponentLoader
 * Created by Raynxxx on 2016/05/23.
 */
public final class ComponentLoader {

    public static void init() {
        Class<?>[] loadWhenAppInit = {
                ClassHelper.class,
                RouterInitializer.class,
                ActiveRecordInitializer.class,
                AopInitializer.class,
                IocInitializer.class
        };

        for (Class<?> cls : loadWhenAppInit) {
            ClassUtil.loadClass(cls.getName());
        }
    }

}
