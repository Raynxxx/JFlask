package com.rayn.jflask.framework.annotation.aop;

import java.lang.annotation.*;

/**
 * Aspect
 * Created by Raynxxx on 2016/08/05.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Aspect {

    String packageName() default "";

    String className() default "";

    Class<? extends Annotation> annotation() default Aspect.class;

}
