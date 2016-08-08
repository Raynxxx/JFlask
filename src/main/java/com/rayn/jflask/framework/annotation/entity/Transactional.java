package com.rayn.jflask.framework.annotation.entity;

import java.lang.annotation.*;

/**
 * Transactional
 * Created by Raynxxx on 2016/08/08.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Transactional {
}
