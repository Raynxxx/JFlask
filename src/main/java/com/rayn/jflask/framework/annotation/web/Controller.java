package com.rayn.jflask.framework.annotation.web;

import java.lang.annotation.*;

/**
 * 控制器类的注解
 * Controller
 * Created by Raynxxx on 2016/05/24.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String namespace() default "";
}
