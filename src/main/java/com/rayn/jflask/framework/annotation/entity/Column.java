package com.rayn.jflask.framework.annotation.entity;

import java.lang.annotation.*;

/**
 * Column
 * Created by Raynxxx on 2016/07/15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
    String name();
    boolean isPrimary() default false;
}
