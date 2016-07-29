package com.rayn.jflask.framework.annotation.entity;

import java.lang.annotation.*;

/**
 * Primary
 * Created by Raynxxx on 2016/07/18.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Primary {

    boolean autoIncrement() default false;
}
