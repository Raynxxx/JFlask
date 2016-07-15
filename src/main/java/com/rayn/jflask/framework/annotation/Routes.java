package com.rayn.jflask.framework.annotation;

import java.lang.annotation.*;

/**
 * Routes
 * Created by Raynxxx on 2016/07/12.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Routes {

    Route[] value();

}
