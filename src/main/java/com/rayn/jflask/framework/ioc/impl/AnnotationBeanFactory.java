package com.rayn.jflask.framework.ioc.impl;

import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.annotation.Bean;
import com.rayn.jflask.framework.annotation.Controller;
import com.rayn.jflask.framework.core.ClassScanner;
import com.rayn.jflask.framework.core.ConfigHelper;
import com.rayn.jflask.framework.ioc.BeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注解实现的 BeanFactory
 * AnnotationBeanFactory
 * Created by Raynxxx on 2016/05/26.
 */
public class AnnotationBeanFactory implements BeanFactory {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationBeanFactory.class);

    // BeanMap
    private static final Map<Class<?>, Object> beanMap = new HashMap<>();

    // 类扫描器
    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();

    // 应用的基础包名
    private static final String basePackage = ConfigHelper.getString("app.base_package");

    static {
        logger.info("[JFlask] BeanFactory 启动");
        try {
            List<Class<?>> classList = classScanner.getClassList(basePackage);
            for (Class<?> clazz : classList) {
                if (clazz.isAnnotationPresent(Bean.class) ||
                        clazz.isAnnotationPresent(Controller.class)) {
                    Object instance = clazz.newInstance();
                    beanMap.put(clazz, instance);
                    logger.debug("[JFlask][BeanFactory] 加入Bean {}", clazz.getName());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("构造BeanFactory出错", e);
        }
    }

    @Override
    public void registerBean(Class<?> clazz, Object bean) {
        beanMap.put(clazz, bean);
    }

    @Override
    public Object getBean(Class<?> clazz) throws Exception {
        if (!beanMap.containsKey(clazz)) {
            throw new RuntimeException("获取实例不存在 " + clazz.getCanonicalName());
        }
        return beanMap.get(clazz);
    }

    @Override
    public boolean containsBean(Class<?> clazz) {
        return beanMap.containsKey(clazz);
    }

    public Map<Class<?>, Object> getAllBeans() {
        return beanMap;
    }
}
