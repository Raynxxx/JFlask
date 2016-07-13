package com.rayn.jflask.framework.core.annotation;

import java.lang.annotation.*;

/**
 * Column
 * Created by Raynxxx on 2016/06/05.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {

    String value();

}
