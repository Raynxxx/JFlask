package com.rayn.jflask.framework.core.annotation;

import java.lang.annotation.*;

/**
 * Table
 * Created by Raynxxx on 2016/06/05.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {

    String value();
}
