package org.rayn.jflask.framework;

import org.rayn.jflask.framework.core.ClassScanner;
import org.rayn.jflask.framework.core.impl.DefaultClassScanner;
import org.rayn.jflask.framework.ioc.BeanFactory;
import org.rayn.jflask.framework.ioc.impl.AnnotationBeanFactory;
import org.rayn.jflask.framework.mvc.HandlerExceptionResolver;
import org.rayn.jflask.framework.mvc.HandlerInvoker;
import org.rayn.jflask.framework.mvc.HandlerMapping;
import org.rayn.jflask.framework.mvc.ViewResolver;
import org.rayn.jflask.framework.mvc.impl.DefaultHandlerExceptionResolver;
import org.rayn.jflask.framework.mvc.impl.DefaultHandlerInvoker;
import org.rayn.jflask.framework.mvc.impl.DefaultHandlerMapping;
import org.rayn.jflask.framework.mvc.impl.DefaultViewResolver;
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
    private static final String BEAN_FACTORY = "framework.bean_factory";
    private static final String HANDLER_MAPPING = "framework.handler_mapping";
    private static final String HANDLER_INVOKER = "framework.handler_invoker";
    private static final String HANDLER_EXCEPTION_RESOLVER = "framework.handler_exception_resolver";
    private static final String VIEW_RESOLVER = "framework.view_resolver";


    public static ClassScanner getClassScanner() {
        return getInstance(CLASS_SCANNER, DefaultClassScanner.class);
    }

    public static BeanFactory getBeanFactory() {
        return getInstance(BEAN_FACTORY, AnnotationBeanFactory.class);
    }

    public static HandlerMapping getHandlerMapping() {
        return getInstance(HANDLER_MAPPING, DefaultHandlerMapping.class);
    }

    public static HandlerInvoker getHandlerInvoker() {
        return getInstance(HANDLER_INVOKER, DefaultHandlerInvoker.class);
    }

    public static HandlerExceptionResolver getHandlerExceptionResolver() {
        return getInstance(HANDLER_EXCEPTION_RESOLVER, DefaultHandlerExceptionResolver.class);
    }

    public static ViewResolver getViewResolver() {
        return getInstance(VIEW_RESOLVER, DefaultViewResolver.class);
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
