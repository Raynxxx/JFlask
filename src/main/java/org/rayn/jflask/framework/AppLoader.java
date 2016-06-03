package org.rayn.jflask.framework;

import org.rayn.jflask.framework.mvc.RouteBuilder;

/**
 * AppLoader
 * Created by Raynxxx on 2016/05/23.
 */
public final class AppLoader {

    static void init() {
        Class<?>[] loadWhenAppInit = {
                RouteBuilder.class
        };
    }

    public static void main(String[] args) {
        init();
    }
}
