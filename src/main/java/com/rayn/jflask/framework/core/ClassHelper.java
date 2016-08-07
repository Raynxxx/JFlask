package com.rayn.jflask.framework.core;

import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.InstanceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * ClassHelper
 * Created by Raynxxx on 2016/08/05.
 */
public class ClassHelper {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(ClassHelper.class);

    // base package
    private static final String basePackage = Constants.BASE_PACKAGE;

    // 类扫描器
    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();

    static {
        logger.info("[JFlask][ClassHelper] 启动");
        getClassList(basePackage);
    }

    public static List<Class<?>> getClassList() {
        return classScanner.getClassList(basePackage);
    }

    public static List<Class<?>> getClassList(String packageName) {
        return classScanner.getClassList(packageName);
    }

    public static List<Class<?>> getClassListByAnnotation(Class<? extends Annotation> annotation) {
        return classScanner.getClassListByAnnotation(basePackage, annotation);
    }

    public static List<Class<?>> getClassListByAnnotation(String packageName,
                                                          Class<? extends Annotation> annotation) {
        return classScanner.getClassListByAnnotation(basePackage, annotation);
    }

    public static List<Class<?>> getClassListBySuper(Class<?> superClass) {
        return classScanner.getClassListBySuper(basePackage, superClass);
    }

    public static List<Class<?>> getClassListBySuper(String packageName,
                                                     Class<?> superClass) {
        return classScanner.getClassListBySuper(basePackage, superClass);
    }
}
