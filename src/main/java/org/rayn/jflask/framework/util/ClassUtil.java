package org.rayn.jflask.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;

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
}
