package org.rayn.jflask.framework.util;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 * StringUtil
 * Created by Raynxxx on 2016/05/22.
 */
public class StringUtil {

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

}
