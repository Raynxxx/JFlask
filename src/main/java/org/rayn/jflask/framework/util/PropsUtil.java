package org.rayn.jflask.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置工具类
 * PropsUtil
 * Created by Raynxxx on 2016/05/22.
 */
public class PropsUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     */
    public static Properties loadProps(String propsPath) {
        Properties props = new Properties();
        InputStream inputStream = null;
        try {
            if (props.isEmpty()) {
                throw new IllegalArgumentException();
            }
            String suffix = ".properties";
            if (propsPath.lastIndexOf(suffix) != -1) {
                propsPath += suffix;
            }
            inputStream = ClassUtil.getClassLoader().getResourceAsStream(propsPath);
            if (inputStream != null) {
                props.load(inputStream);
            }
        } catch (Exception e) {
            logger.error("加载属性文件错误:", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("释放资源错误:", e);
            }
        }
        return props;
    }

    /**
     * 加载属性文件, 并转为 Map
     */
    public static Map<String, String> loadPropsToMap(String propsPath) {
        Map<String, String> map = new HashMap<>();
        Properties props = loadProps(propsPath);
        for (String key : props.stringPropertyNames()) {
            map.put(key, props.getProperty(key));
        }
        return map;
    }

    /**
     * 获取字符串型属性, 默认为 ""
     */
    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    /**
     * 获取字符串型属性, 可设置默认值
     */
    public static String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * 获取整数型属性, 默认 0
     */
    public static int getInteger(Properties props, String key) {
        return getInteger(props, key, 0);
    }

    /**
     * 获取整数型属性, 可设置默认值
     */
    public static int getInteger(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.containsKey(key)) {
            value = Integer.parseInt(props.getProperty(key));
        }
        return value;
    }

    /**
     * 获取布尔型属性, 默认 false
     */
    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    /**
     * 获取布尔型属性, 可设置默认值
     */
    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)) {
            value = Boolean.parseBoolean(props.getProperty(key));
        }
        return value;
    }
}
