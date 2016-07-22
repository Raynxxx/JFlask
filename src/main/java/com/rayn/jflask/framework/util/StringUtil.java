package com.rayn.jflask.framework.util;

import com.rayn.jflask.framework.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 * StringUtil
 * Created by Raynxxx on 2016/05/22.
 */
public class StringUtil {

    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static final String SEPARATOR = String.valueOf((char) 29);

    /**
     * 是否为空
     */
    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    /**
     * 是否非空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 获取值, 否则返回默认值
     */
    public static String getOrDefault(String str, String defaultValue) {
        return StringUtils.defaultIfEmpty(str, defaultValue);
    }

    /**
     * 是否为数字
     */
    public static boolean isNumber(String str) {
        return NumberUtils.isNumber(str);
    }

    /**
     * 是否为十进制整数
     */
    public static boolean isDigits(String str) {
        return NumberUtils.isDigits(str);
    }

    /**
     * 将驼峰式命名转换成下划线风格
     */
    public static String camelToUnderline(String str) {
        StringBuilder builder = new StringBuilder(str);
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        for (int i = 0; matcher.find(); ++i) {
            builder.replace(matcher.start() + i, matcher.end() + i,
                    "_" + matcher.group().toLowerCase());
        }
        if (builder.charAt(0) == '_') {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    /**
     * 将下划线命名转换成驼峰式风格
     */
    public static String underlineToCamel(String str) {
        StringBuilder builder = new StringBuilder(str);
        Matcher matcher = Pattern.compile("_[a-z]").matcher(str);
        for (int i = 0; matcher.find(); ++i) {
            builder.replace(matcher.start() - i, matcher.end() - i,
                    matcher.group().substring(1).toUpperCase());
        }
        if (Character.isUpperCase(builder.charAt(0))) {
            builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
        }
        return builder.toString();
    }

    /**
     * 首字母大写
     */
    public static String capitalize(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 替换所有匹配的部分
     */
    public static String replaceAll(String str, String regex, String replacement) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, replacement);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 数组合并为字符串
     */
    public static String join(Object[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            sb.append(array[i]);
            if (i != array.length - 1) {
                sb.append(StringUtil.SEPARATOR);
            }
        }
        return sb.toString();
    }

    /**
     * 数组合并为字符串，可设置分隔符
     */
    public static String join(Object[] array, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            sb.append(array[i]);
            if (i != array.length - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * 从 InputStream 读出字符串
     */
    public static String toString(InputStream is) {
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Constants.UTF8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            logger.error("[JFlask] InputStream to String error", e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * 将 URL 编码
     */
    public static String encodeURL(String origin) {
        String target;
        try {
            target = URLEncoder.encode(origin, Constants.UTF8);
        } catch (Exception e) {
            logger.error("[JFlask] {} 编码出错", origin, e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将 URL 解码
     */
    public static String decodeURL(String origin) {
        String target;
        try {
            target = URLDecoder.decode(origin, Constants.UTF8);
        } catch (Exception e) {
            logger.error("[JFlask] {} 解码出错", origin, e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 获取正则捕获的所有组名
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Integer> getNamedGroups(Pattern pattern) {
        Map<String, Integer> ret = null;
        try {
            Method namedGroups = Pattern.class.getDeclaredMethod("namedGroups");
            namedGroups.setAccessible(true);
            ret = (Map<String, Integer>) namedGroups.invoke(pattern);
        } catch (Exception e) {
            logger.error("[JFlask] getNamedGroups Error", e);
        }
        return ret;
    }

}
