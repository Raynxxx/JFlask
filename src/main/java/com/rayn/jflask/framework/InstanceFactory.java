package com.rayn.jflask.framework;

import com.rayn.jflask.framework.core.ClassScanner;
import com.rayn.jflask.framework.core.ConfigHelper;
import com.rayn.jflask.framework.core.impl.DefaultClassScanner;
import com.rayn.jflask.framework.ioc.BeanFactory;
import com.rayn.jflask.framework.ioc.impl.AnnotationBeanFactory;
import com.rayn.jflask.framework.mvc.handler.HandlerExceptionResolver;
import com.rayn.jflask.framework.mvc.handler.HandlerInvoker;
import com.rayn.jflask.framework.mvc.handler.HandlerMapping;
import com.rayn.jflask.framework.mvc.handler.ResultResolver;
import com.rayn.jflask.framework.mvc.handler.impl.DefaultHandlerExceptionResolver;
import com.rayn.jflask.framework.mvc.handler.impl.DefaultHandlerInvoker;
import com.rayn.jflask.framework.mvc.handler.impl.DefaultHandlerMapping;
import com.rayn.jflask.framework.mvc.handler.impl.DefaultResultResolver;
import com.rayn.jflask.framework.orm.DataSourceProvider;
import com.rayn.jflask.framework.orm.impl.DruidDataSourceProvider;
import com.rayn.jflask.framework.util.ClassUtil;
import com.rayn.jflask.framework.util.StringUtil;
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

    // core
    private static final String CLASS_SCANNER = "framework.class_scanner";

    // ioc
    private static final String BEAN_FACTORY = "framework.bean_factory";

    // mvc
    private static final String HANDLER_MAPPING = "framework.handler_mapping";
    private static final String HANDLER_INVOKER = "framework.handler_invoker";
    private static final String VIEW_RESOLVER = "framework.view_resolver";
    private static final String HANDLER_EXCEPTION_RESOLVER = "framework.handler_exception_resolver";

    // db
    private static final String DATA_SOURCE_PROVIDER = "framework.data_source_provider";


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

    public static ResultResolver getViewResolver() {
        return getInstance(VIEW_RESOLVER, DefaultResultResolver.class);
    }

    public static HandlerExceptionResolver getHandlerExceptionResolver() {
        return getInstance(HANDLER_EXCEPTION_RESOLVER, DefaultHandlerExceptionResolver.class);
    }

    public static DataSourceProvider getDataSourceProvider() {
        return getInstance(DATA_SOURCE_PROVIDER, DruidDataSourceProvider.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(String key, Class<T> defaultImpl) {
        if (cache.containsKey(key)) {
            return (T) cache.get(key);
        }
        String implName = ConfigHelper.getString(key);
        if (StringUtil.isEmpty(implName)) {
            implName = defaultImpl.getName();
        }
        T instance;
        try {
            instance = (T) ClassUtil.loadClass(implName).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("[JFlask] 实例化错误: {}", key);
            throw new RuntimeException(e);
        }
        if (instance != null) {
            cache.put(key, instance);
        }
        return instance;
    }
}
