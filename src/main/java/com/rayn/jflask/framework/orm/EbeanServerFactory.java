package com.rayn.jflask.framework.orm;

import com.avaje.ebean.EbeanServer;

import java.util.Properties;

/**
 * EbeanServerFactory
 * Created by Raynxxx on 2016/06/10.
 */
public interface EbeanServerFactory {

    void init(Properties properties);

    Class<?> getEbeanServerObjectType();

    EbeanServer getEbeanServerObject();
}
