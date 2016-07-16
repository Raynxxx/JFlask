package com.rayn.jflask.framework.core;

import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.Properties;

/**
 * 配置工厂
 * ConfigHelper
 * Created by Raynxxx on 2016/05/23.
 */
public class ConfigHelper {

    private static final Logger logger = LoggerFactory.getLogger(ConfigHelper.class);

    private static final Properties configProps;

    static {
        logger.info("[JFlask] ConfigHelper 启动, 加载配置文件: {}", Constants.CONFIG_PROPS);
        configProps = PropsUtil.loadProps(Constants.CONFIG_PROPS);
        Enumeration<?> keys = configProps.propertyNames();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            logger.debug("[JFlask][{}] {} => {}", Constants.CONFIG_PROPS,
                    key, configProps.get(key));
        }
    }

    public static Properties getConfig() {
        return configProps;
    }

    /**
     * 取得字符串值, 默认 null
     */
    public static String getString(String str) {
        return PropsUtil.getString(configProps, str);
    }

    /**
     * 取得字符串值, 含默认值
     */
    public static String getString(String str, String defaultValue) {
        return PropsUtil.getString(configProps, str, defaultValue);
    }

    /**
     * 取得布尔值, 默认 false
     */
    public static boolean getBoolean(String str) {
        return PropsUtil.getBoolean(configProps, str);
    }

    /**
     * 取得布尔值, 含默认值
     */
    public static boolean getBoolean(String str, boolean defaultValue) {
        return PropsUtil.getBoolean(configProps, str, defaultValue);
    }

    /**
     * 取得整数值, 默认 0
     */
    public static int getInteger(String str) {
        return PropsUtil.getInteger(configProps, str);
    }

    /**
     * 取得整数值, 含默认值
     */
    public static int getInteger(String str, int defaultValue) {
        return PropsUtil.getInteger(configProps, str, defaultValue);
    }
}
