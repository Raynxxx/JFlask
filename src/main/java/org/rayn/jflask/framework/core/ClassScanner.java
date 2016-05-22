package org.rayn.jflask.framework.core;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 类扫描器接口
 * ClassScanner
 * Created by Raynxxx on 2016/05/21.
 */
public interface ClassScanner {

    /**
     * 取得指定包下的所有类
     */
    List<Class<?>> getClassList(String packageName);

    /**
     * 取得指定包下含有指定注解的所有类
     */
    List<Class<?>> getClassListByAnnotation(String packageName,
                                            Class<? extends Annotation> annotation);

    /**
     * 取得指定包下, 指定父类或者接口的所有类
     */
    List<Class<?>> getClassListBySuper(String packageName, Class<?> superClass);
}
