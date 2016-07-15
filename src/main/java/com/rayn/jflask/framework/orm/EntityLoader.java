package com.rayn.jflask.framework.orm;

import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.annotation.Entity;
import com.rayn.jflask.framework.core.ClassScanner;
import com.rayn.jflask.framework.core.ConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * EntityLoader
 * Created by Raynxxx on 2016/07/15.
 */
public class EntityLoader {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(EntityLoader.class);

    static {
        logger.info("[JFlask] EntityLoader 启动");
    }

    private static List<Class<?>> getEntityList() {
        final ClassScanner classScanner = InstanceFactory.getClassScanner();
        final String basePackage = ConfigHelper.getString("app.base_package");
        return classScanner.getClassListByAnnotation(basePackage, Entity.class);
    }
}
