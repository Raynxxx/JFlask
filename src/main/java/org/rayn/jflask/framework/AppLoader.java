package org.rayn.jflask.framework;

import org.rayn.jflask.framework.mvc.RouteBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AppLoader
 * Created by Raynxxx on 2016/05/23.
 */
public final class AppLoader {

    void init() {
        Class<?>[] loadWhenAppInit = {
                RouteBuilder.class
        };
    }

}
