package com.rayn.jflask.framework.annotation;

import java.lang.annotation.*;

/**
 * URL路由注解
 * Route
 * Created by Raynxxx on 2016/05/24.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(Routes.class)
public @interface Route {

    String value();

    String[] method() default "GET";
}
