package com.rayn.jflask.framework.annotation.entity;

import java.lang.annotation.*;

/**
 * SkipJson
 * Created by Raynxxx on 2016/07/14.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipJson {
}
