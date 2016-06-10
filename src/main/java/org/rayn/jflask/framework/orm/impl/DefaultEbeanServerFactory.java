package org.rayn.jflask.framework.orm.impl;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;
import org.rayn.jflask.framework.core.ConfigHelper;
import org.rayn.jflask.framework.orm.EbeanServerFactory;

/**
 * DefaultEbeanServerFactory
 * Created by Raynxxx on 2016/06/10.
 */
public class DefaultEbeanServerFactory implements EbeanServerFactory {

    private static final ServerConfig serverConfig = new ServerConfig();
    private static EbeanServer ebeanServer;

    static {
        serverConfig.setName("db");
        serverConfig.loadFromProperties(ConfigHelper.getConfig());
        serverConfig.setDefaultServer(true);
        serverConfig.setRegister(true);
        ebeanServer = com.avaje.ebean.EbeanServerFactory.create(serverConfig);
    }

    @Override
    public EbeanServer getEbeanServer() {
        return ebeanServer;
    }
}
