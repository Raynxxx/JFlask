package com.rayn.jflask.framework.ioc;

/**
 * FactoryBean
 * Created by Raynxxx on 2016/07/14.
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;
    Class<?> getObjectType();

}
