package com.rayn.jflask.framework;

import com.rayn.jflask.framework.ioc.BeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Application
 * Created by Raynxxx on 2016/08/05.
 */
public class Application {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    // beanFactory
    private final BeanFactory beanFactory = InstanceFactory.getBeanFactory();

    public void init() {
    }

    public static Logger getLogger() {
        return logger;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
