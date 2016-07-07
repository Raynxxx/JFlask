package com.rayn.jflask.framework.ioc;

import java.util.Map;

/**
 * BeanFactory
 * Created by Raynxxx on 2016/05/26.
 */
public interface BeanFactory {

    void registerBean(Class<?> clazz, Object bean);

    Object getBean(Class<?> clazz) throws Exception;

    boolean containsBean(Class<?> clazz);

    public Map<Class<?>, Object> getAllBeans();
}
