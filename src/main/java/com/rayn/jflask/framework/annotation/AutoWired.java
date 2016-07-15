package com.rayn.jflask.framework.annotation;

import java.lang.annotation.*;

/**
 * AutoWired
 * Created by Raynxxx on 2016/05/26.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoWired {
}
