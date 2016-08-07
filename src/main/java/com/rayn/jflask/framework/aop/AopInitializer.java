package com.rayn.jflask.framework.aop;

import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.annotation.aop.Aspect;
import com.rayn.jflask.framework.annotation.aop.Service;
import com.rayn.jflask.framework.aop.proxy.Proxy;
import com.rayn.jflask.framework.aop.proxy.ProxyManager;
import com.rayn.jflask.framework.core.ClassHelper;
import com.rayn.jflask.framework.ioc.BeanFactory;
import com.rayn.jflask.framework.query.TransactionProxy;
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

    private static final BeanFactory beanFactory = InstanceFactory.getBeanFactory();

    static {
        logger.info("[JFlask][AopInitializer] 启动");
        try {
            // targetClass => Array(proxyClass)
            Map<Class<?>, List<Proxy>> targetProxyListMap = loadTaqrgetProxyListMap();
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetProxyListMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyClassList = targetEntry.getValue();

                logger.debug("[JFlask][AOP] {} <==> {} proxy", targetClass.getName(), proxyClassList.size());

                Object proxyInstance = ProxyManager.createProxy(targetClass, proxyClassList);
                beanFactory.registerBean(targetClass, proxyInstance);

            }
        } catch (Exception e) {
            logger.error("[JFlask] 初始化 AopInitializer 失败!", e);
            throw new RuntimeException(e);
        }
    }

    private static Map<Class<?>, List<Proxy>> loadTaqrgetProxyListMap() throws Exception {
        Map<Class<?>, List<Class<?>>> proxyMap = new LinkedHashMap<>();
        Map<Class<?>, List<Proxy>> targetMap = new LinkedHashMap<>();
        loadAspectProxy(proxyMap);
        loadTransactionProxy(proxyMap);
        for (Map.Entry<Class<?>, List<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            List<Class<?>> targetClassList = proxyEntry.getValue();
            // 对每个 target 类，扩展它的 proxy list
            for (Class<?> targetClass : targetClassList) {
                Proxy curProxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(curProxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(curProxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
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
                // 指定包名，指定类名，唯一确定类
                targetList.add(ClassUtil.loadClass(packageName + '.' + className, false));
            } else {
                if (!annotation.equals(Aspect.class)) {
                    // 指定包名，指定注解的所有类
                    targetList.addAll(ClassHelper.getClassListByAnnotation(
                            packageName, annotation));
                } else {
                    // 指定包名所有类
                    targetList.addAll(ClassHelper.getClassList(
                            packageName));
                }
            }
        } else {
            // 指定注解的所有类
            if (!annotation.equals(Aspect.class)) {
                targetList.addAll(ClassHelper.getClassListByAnnotation(annotation));
            }
        }
        return targetList;
    }

    private static void loadTransactionProxy(Map<Class<?>, List<Class<?>>> proxyMap) {
        List<Class<?>> transactionProxyClassList = ClassHelper.getClassListByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class, transactionProxyClassList);
    }
}
