package org.rayn.jflask.framework;

import org.rayn.jflask.framework.core.ClassScanner;
import org.rayn.jflask.framework.core.impl.DefaultClassScanner;
import org.rayn.jflask.framework.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * InstanceFactory
 * Created by Raynxxx on 2016/05/21.
 */
public class InstanceFactory {

    private static final Logger logger = LoggerFactory.getLogger(InstanceFactory.class);
    private static final Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

    private static final String CLASS_SCANNER = "framework.class_scanner";

    public static ClassScanner getClassScanner() {
        return getInstance(CLASS_SCANNER, DefaultClassScanner.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(String key, Class<T> defaultImpl) {
        if (cache.containsKey(key)) {
            return (T) cache.get(key);
        }
        String implName = defaultImpl.getName();
        T instance = null;
        try {
            instance = (T) ClassUtil.loadClass(implName).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("生成实例对象错误", e);
            throw new RuntimeException(e);
        }
        if (instance != null) {
            cache.put(key, instance);
        }
        return instance;
    }
}
