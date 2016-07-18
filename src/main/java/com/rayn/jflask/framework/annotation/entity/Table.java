package com.rayn.jflask.framework.annotation.entity;

import java.lang.annotation.*;

/**
 * TableInfo
 * Created by Raynxxx on 2016/07/15.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    String name();
}
