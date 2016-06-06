package org.rayn.jflask.framework;

import org.rayn.jflask.framework.ioc.IOCBuilder;
import org.rayn.jflask.framework.mvc.RouteBuilder;
import org.rayn.jflask.framework.orm.ORMBuilder;

/**
 * AppLoader
 * Created by Raynxxx on 2016/05/23.
 */
public final class AppLoader {

    static void init() {
        Class<?>[] loadWhenAppInit = {
                RouteBuilder.class,
                IOCBuilder.class,
                ORMBuilder.class
        };
    }

    public static void main(String[] args) {
        init();
    }
}
