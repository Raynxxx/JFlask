package com.rayn.jflask.framework.orm.impl;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;
import com.rayn.jflask.framework.core.ConfigHelper;
import com.rayn.jflask.framework.orm.EbeanServerFactory;

import java.util.Properties;

/**
 * DefaultEbeanServerFactory
 * Created by Raynxxx on 2016/06/10.
 */
public class DefaultEbeanServerFactory implements EbeanServerFactory {

    private ServerConfig serverConfig = new ServerConfig();
    private EbeanServer ebeanServer;

    @Override
    public void init(Properties properties) {
        serverConfig.setName("db");
        serverConfig.loadFromProperties();
        serverConfig.setDefaultServer(true);
        serverConfig.setRegister(true);
        ebeanServer = com.avaje.ebean.EbeanServerFactory.create(serverConfig);
    }

    @Override
    public Class<?> getEbeanServerObjectType() {
        return EbeanServer.class;
    }

    @Override
    public EbeanServer getEbeanServerObject() {
        return ebeanServer;
    }
}
