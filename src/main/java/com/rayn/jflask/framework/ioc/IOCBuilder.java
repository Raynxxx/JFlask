package com.rayn.jflask.framework.ioc;

import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.annotation.AutoWired;
import com.rayn.jflask.framework.core.ClassScanner;
import com.rayn.jflask.framework.core.ConfigHelper;
import com.rayn.jflask.framework.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 控制反转构建器
 * IOCBuilder
 * Created by Raynxxx on 2016/05/26.
 */
public class IOCBuilder {

    private static final Logger logger = LoggerFactory.getLogger(IOCBuilder.class);

    // bean 工厂
    private static final BeanFactory beanFactory = InstanceFactory.getBeanFactory();

    // 类扫描器
    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();

    // 应用的基础包名
    private static final String basePackage = ConfigHelper.getString("app.base_package");

    static {
        logger.info("[JFlask] IOCBuilder 启动, Dependence Injection Processing");
        try {
            Map<Class<?>, Object> beanMap = beanFactory.getAllBeans();
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();

                // 获取 bean 类的所有字段
                Field[] beanFields = beanClass.getDeclaredFields();
                if (CollectionUtil.isEmpty(beanFields)) {
                    continue;
                }
                for (Field beanField : beanFields) {
                    // 当前字段存在 AutoWired 注解
                    if (beanField.isAnnotationPresent(AutoWired.class)) {
                        // 获取字段类型
                        Class<?> injectInterface = beanField.getType();
                        // 获取对应类型的第一个实现类
                        Class<?> implClass = getImplementClass(injectInterface);
                        if (implClass != null) {
                            // 从 beanMap 获取实例
                            Object instance = beanMap.get(implClass);
                            if (instance != null) {
                                beanField.setAccessible(true);
                                beanField.set(beanInstance, instance);
                                logger.debug("[JFlask][IOCBuilder] 注入 {} => {}#{}", implClass.getName(),
                                        beanClass.getName(), beanField.getName());
                            } else {
                                throw new RuntimeException(beanClass.getCanonicalName() + " / " +
                                        beanField.getName() + " 注入失败, 不存在实现类");
                            }
                        } else {
                            // TODO (to throw exception whether or not)
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("IOCBuilder构建过程出错", e);
        }

    }

    public static Class<?> getImplementClass(Class<?> interfaceClass) {
        Class<?> implClass = null;
        List<Class<?>> implClassList = classScanner.getClassListBySuper(basePackage, interfaceClass);
        if (CollectionUtil.isNotEmpty(implClassList)) {
            implClass = implClassList.get(0);
        }
        return implClass;
    }
}
