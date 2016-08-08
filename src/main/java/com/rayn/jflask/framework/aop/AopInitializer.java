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
import java.util.*;

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
            Map<Class<?>, List<Proxy>> targetMap = loadTargetProxyListMap();
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyClassList = targetEntry.getValue();

                logger.debug("[JFlask][AOP] {} <==> {} proxy", targetClass.getName(),
                        proxyClassList.size());

                Object proxyInstance = ProxyManager.createProxy(targetClass, proxyClassList);
                beanFactory.registerBean(targetClass, proxyInstance);
            }
        } catch (Exception e) {
            logger.error("[JFlask] 初始化 AopInitializer 失败!", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载所有切面目标类，以及各自的 Aspect
     */
    private static Map<Class<?>, List<Proxy>> loadTargetProxyListMap() throws Exception {
        // load proxyMap (proxyClass => Array(targetClass))
        Map<Class<?>, List<Class<?>>> proxyMap = new LinkedHashMap<>();
        loadAspectProxy(proxyMap);
        loadTransactionProxy(proxyMap);

        // build targetMap (targetClass => Array(proxyClass))
        Map<Class<?>, List<Proxy>> targetMap = new LinkedHashMap<>();
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

    /**
     * 加载所有 @Aspect 注解实现类
     */
    private static void loadAspectProxy(Map<Class<?>, List<Class<?>>> proxyMap) throws Exception {
        List<Class<?>> aspectProxyClassList = ClassHelper.getClassListByAnnotation(Aspect.class);
        // 排序
        aspectProxyClassList.sort(new Comparator<Class<?>>() {
            @Override
            public int compare(Class<?> o1, Class<?> o2) {
                Aspect aspect1 = o1.getAnnotation(Aspect.class);
                Aspect aspect2 = o2.getAnnotation(Aspect.class);
                if (aspect1.order() != aspect2.order()) {
                    return aspect1.order() - aspect2.order();
                }
                return o1.hashCode() - o1.hashCode();
            }
        });
        for (Class<?> aspectProxyClass : aspectProxyClassList) {
            Aspect aspect = aspectProxyClass.getAnnotation(Aspect.class);
            List<Class<?>> targetList = loadAspectTargetList(aspect);
            proxyMap.put(aspectProxyClass, targetList);
        }
    }

    /**
     * 加载 @Aspect 注解指定的类列表
     */
    private static List<Class<?>> loadAspectTargetList(Aspect aspect) {
        List<Class<?>> targetList = new ArrayList<>();
        String targetPackage = aspect.targetPackage();
        String targetClass = aspect.targetClass();
        Class<? extends Annotation> targetAnnotation = aspect.targetAnnotation();
        if (StringUtil.isNotEmpty(targetPackage)) {
            if (StringUtil.isNotEmpty(targetClass)) {
                // 指定包名，指定类名，唯一确定类
                targetList.add(ClassUtil.loadClass(targetPackage + '.' + targetClass, false));
            } else {
                if (!targetAnnotation.equals(Aspect.class)) {
                    // 指定包名，指定注解的所有类
                    targetList.addAll(ClassHelper.getClassListByAnnotation(
                            targetPackage, targetAnnotation));
                } else {
                    // 指定包名所有类
                    targetList.addAll(ClassHelper.getClassList(
                            targetPackage));
                }
            }
        } else {
            // 指定注解的所有类
            if (!targetAnnotation.equals(Aspect.class)) {
                targetList.addAll(ClassHelper.getClassListByAnnotation(targetAnnotation));
            }
        }
        return targetList;
    }

    /**
     * 加载所有 Service 注解类 (实现 Transactional)
     */
    private static void loadTransactionProxy(Map<Class<?>, List<Class<?>>> proxyMap) {
        List<Class<?>> transactionProxyClassList = ClassHelper.getClassListByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class, transactionProxyClassList);
    }
}
