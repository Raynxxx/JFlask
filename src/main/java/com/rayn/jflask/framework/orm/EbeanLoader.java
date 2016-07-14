package com.rayn.jflask.framework.orm;

import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.core.ConfigHelper;
import com.rayn.jflask.framework.ioc.BeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EbeanLoader
 * Created by Raynxxx on 2016/07/14.
 */
public class EbeanLoader {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(EbeanLoader.class);

    // beanFactory
    private static final BeanFactory beanFactory = InstanceFactory.getBeanFactory();

    // EbeanServerFactory
    private static final EbeanServerFactory ebeanServerFactory = InstanceFactory.getEbeanServerFactory();

    static {
        logger.info("[JFlask] EbeanServer 加载");
        ebeanServerFactory.init(ConfigHelper.getConfig());
        beanFactory.registerBean(ebeanServerFactory.getEbeanServerObjectType(),
                ebeanServerFactory.getEbeanServerObject());
    }
}
