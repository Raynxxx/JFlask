package com.rayn.jflask.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * 操作类的工具
 * ClassUtil
 * Created by Raynxxx on 2016/05/14.
 */
public class ClassUtil {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取当前的类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获取 classpath 根路径
     */
    public static String getClassPath() {
        String classpath = "";
        URL resource = getClassLoader().getResource("");
        if (resource != null) {
            classpath = resource.getPath();
        }
        return classpath;
    }

    /**
     * 加载类 (自动初始化)
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    /**
     * 加载类
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            logger.error("加载类" + className + "错误", e);
            throw new RuntimeException(e);
        }
        return clazz;
    }

    /**
     * 判断是否为 int/Integer 类型
     */
    public static boolean isBoolean(Class<?> clazz) {
        return clazz.equals(boolean.class) || clazz.equals(Boolean.class);
    }

    /**
     * 判断是否为 int/Integer 类型
     */
    public static boolean isInteger(Class<?> clazz) {
        return clazz.equals(int.class) || clazz.equals(Integer.class);
    }

    /**
     * 判断是否为 long/Long 类型
     */
    public static boolean isLong(Class<?> clazz) {
        return clazz.equals(long.class) || clazz.equals(Long.class);
    }

    /**
     * 判断是否为 float/Float 类型
     */
    public static boolean isFloat(Class<?> clazz) {
        return clazz.equals(float.class) || clazz.equals(Float.class);
    }

    /**
     * 判断是否为 double/Double 类型
     */
    public static boolean isDouble(Class<?> clazz) {
        return clazz.equals(double.class) || clazz.equals(Double.class);
    }

    /**
     * 判断是否为 String 类型
     */
    public static boolean isString(Class<?> clazz) {
        return clazz.equals(String.class);
    }

    /**
     * 转换为 boolean
     */
    public static boolean toBoolean(Object obj) {
        return toBoolean(obj, false);
    }

    /**
     * 转换为 boolean, 带有默认值
     */
    public static boolean toBoolean(Object obj, boolean defaultValue) {
        boolean ret = defaultValue;
        if (obj != null) {
            String strInt = toString(obj);
            if (StringUtil.isNotEmpty(strInt)) {
                try {
                    ret = Boolean.parseBoolean(strInt);
                } catch (Exception e) {
                    ret = defaultValue;
                }
            }
        }
        return ret;
    }

    /**
     * 转换为 int
     */
    public static int toInteger(Object obj) {
        return toInteger(obj, 0);
    }

    /**
     * 转换为 int, 带有默认值
     */
    public static int toInteger(Object obj, int defaultValue) {
        int ret = defaultValue;
        if (obj != null) {
            String strInt = toString(obj);
            if (StringUtil.isNotEmpty(strInt)) {
                try {
                    ret = Integer.parseInt(strInt);
                } catch (Exception e) {
                    ret = defaultValue;
                }
            }
        }
        return ret;
    }

    /**
     * 转换为 long
     */
    public static long toLong(Object obj) {
        return toLong(obj, 0);
    }

    /**
     * 转换为 long, 带有默认值
     */
    public static long toLong(Object obj, long defaultValue) {
        long ret = defaultValue;
        if (obj != null) {
            String strInt = toString(obj);
            if (StringUtil.isNotEmpty(strInt)) {
                try {
                    ret = Long.parseLong(strInt);
                } catch (Exception e) {
                    ret = defaultValue;
                }
            }
        }
        return ret;
    }

    /**
     * 转换为 long
     */
    public static double toDouble(Object obj) {
        return toDouble(obj, 0);
    }

    /**
     * 转换为 long, 带有默认值
     */
    public static double toDouble(Object obj, double defaultValue) {
        double ret = defaultValue;
        if (obj != null) {
            String strInt = toString(obj);
            if (StringUtil.isNotEmpty(strInt)) {
                try {
                    ret = Double.parseDouble(strInt);
                } catch (Exception e) {
                    ret = defaultValue;
                }
            }
        }
        return ret;
    }

    /**
     * 转换为 long
     */
    public static float toFloat(Object obj) {
        return toFloat(obj, 0);
    }

    /**
     * 转换为 long, 带有默认值
     */
    public static float toFloat(Object obj, float defaultValue) {
        float ret = defaultValue;
        if (obj != null) {
            String strInt = toString(obj);
            if (StringUtil.isNotEmpty(strInt)) {
                try {
                    ret = Float.parseFloat(strInt);
                } catch (Exception e) {
                    ret = defaultValue;
                }
            }
        }
        return ret;
    }

    /**
     * 转换为 String 类型
     */
    public static String toString(Object obj) {
        return toString(obj, "");
    }

    /**
     * 转换为 String 类型, 带有默认值
     */
    public static String toString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }
}
