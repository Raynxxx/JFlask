package com.rayn.jflask.framework.annotation;

import java.lang.annotation.*;

/**
 * Entity
 * Created by Raynxxx on 2016/07/15.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Entity {
    String name();
}
