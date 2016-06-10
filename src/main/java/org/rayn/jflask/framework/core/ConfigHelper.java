package org.rayn.jflask.framework.core;

import org.rayn.jflask.framework.Constants;
import org.rayn.jflask.framework.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 配置工厂
 * ConfigHelper
 * Created by Raynxxx on 2016/05/23.
 */
public class ConfigHelper {

    private static final Logger logger = LoggerFactory.getLogger(ConfigHelper.class);

    private static final Properties config = PropsUtil.loadProps(Constants.CONFIG_PROPS);

    public static Properties getConfig() {
        return config;
    }

    /**
     * 取得字符串值, 默认 null
     */
    public static String getString(String str) {
        return PropsUtil.getString(config, str);
    }

    /**
     * 取得字符串值, 含默认值
     */
    public static String getString(String str, String defaultValue) {
        return PropsUtil.getString(config, str, defaultValue);
    }

    /**
     * 取得布尔值, 默认 false
     */
    public static boolean getBoolean(String str) {
        return PropsUtil.getBoolean(config, str);
    }

    /**
     * 取得布尔值, 含默认值
     */
    public static boolean getBoolean(String str, boolean defaultValue) {
        return PropsUtil.getBoolean(config, str, defaultValue);
    }

    /**
     * 取得整数值, 默认 0
     */
    public static int getInteger(String str) {
        return PropsUtil.getInteger(config, str);
    }

    /**
     * 取得整数值, 含默认值
     */
    public static int getInteger(String str, int defaultValue) {
        return PropsUtil.getInteger(config, str);
    }
}
