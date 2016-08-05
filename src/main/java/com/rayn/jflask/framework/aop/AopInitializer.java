package com.rayn.jflask.framework.aop;

import com.rayn.jflask.framework.annotation.aop.Aspect;
import com.rayn.jflask.framework.core.ClassHelper;
import com.rayn.jflask.framework.util.ClassUtil;
import com.rayn.jflask.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * AopInitializer
 * Created by Raynxxx on 2016/08/05.
 */
public class AopInitializer {

    private static final Logger logger = LoggerFactory.getLogger(AopInitializer.class);

    static {
        try {
            Map<Class<?>, List<Class<?>>> proxyMap = loadProxy();
        } catch (Exception e) {
            logger.error("[JFlask] 初始化 AopInitializer 失败!", e);
        }

    }

    private static Map<Class<?>, List<Class<?>>> loadProxy() throws Exception {
        Map<Class<?>, List<Class<?>>> proxyMap = new LinkedHashMap<>();
        loadAspectProxy(proxyMap);
        loadTransactionProxy(proxyMap);
        return proxyMap;
    }

    private static void loadAspectProxy(Map<Class<?>, List<Class<?>>> proxyMap) throws Exception {
        List<Class<?>> aspectProxyClassList = ClassHelper.getClassListBySuper(AspectProxy.class);
        for (Class<?> aspectProxyClass : aspectProxyClassList) {
            if (aspectProxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = aspectProxyClass.getAnnotation(Aspect.class);
                List<Class<?>> targetList = loadAspectTargetList(aspect);
                proxyMap.put(aspectProxyClass, targetList);
            }
        }
    }

    private static List<Class<?>> loadAspectTargetList(Aspect aspect) {
        List<Class<?>> targetList = new ArrayList<>();
        String packageName = aspect.packageName();
        String className = aspect.className();
        Class<? extends Annotation> annotation = aspect.annotation();
        if (StringUtil.isNotEmpty(packageName)) {
            if (StringUtil.isNotEmpty(className)) {
                targetList.add(ClassUtil.loadClass(packageName + '.' + className, false));
            } else {
                if (!annotation.equals(Aspect.class)) {
                    targetList.addAll(ClassHelper.getClassListByAnnotation(
                            packageName, annotation));
                } else {
                    targetList.addAll(ClassHelper.getClassList(
                            packageName));
                }
            }
        } else {
            if (!annotation.equals(Aspect.class)) {
                targetList.addAll(ClassHelper.getClassListByAnnotation(annotation));
            }
        }
        return targetList;
    }

    private static void loadTransactionProxy(Map<Class<?>, List<Class<?>>> proxyMap) {

    }
}
