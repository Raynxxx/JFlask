package com.rayn.jflask.framework.orm.helper;

import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.core.exception.QueryException;
import com.rayn.jflask.framework.orm.TableMapping;
import com.rayn.jflask.framework.orm.model.TableInfo;
import com.rayn.jflask.framework.util.CollectionUtil;
import com.rayn.jflask.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SqlHelper
 * Created by Raynxxx on 2016/08/02.
 */
public class SqlHelper {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(SqlHelper.class);

    // tableMapping
    private static final TableMapping tableMapping = TableMapping.me();

    // conditionRegex & Pattern
    private static final String conditionRegex =
            "([a-zA-Z_]+[a-zA-Z0-9_]+)\\s+      # field variable\n" +
            "(\n" +
            "   =|!=|<>|>|>=|<|<=|              # relation operator\n" +
            "   (?i)like|in|between             # case insensitive operator\n" +
            ")\\s+";

    private static final Pattern conditionPattern = Pattern.compile(conditionRegex, Pattern.COMMENTS);

    // conditionRegex & Pattern
    private static final String orderByRegex = "^ASC|DESC$";

    private static final Pattern orderByPattern = Pattern.compile(orderByRegex, Pattern.CASE_INSENSITIVE);


    public static <T> TableInfo getTableInfo(Class<T> entity) {
        return tableMapping.getTableInfo(entity);
    }

    public static  <T> String getTableName(Class<T> entity) {
        return tableMapping.getTableName(entity);
    }

    /**
     * 输出 SQL 语句
     */
    public static void outputSQL(String sql) {
        if (Constants.JDBC.SHOW_SQL) {
            logger.debug("[JFlask][SQL] ===> {}", sql);
        }
    }

    /**
     * 根据映射信息转换 Select SQL 语句
     */
    public static String transferSelect(Class<?> entityClass, String selectFields) {
        if (StringUtil.isEmpty(selectFields)) return "";
        String[] fields = selectFields.split(",");
        for (int i = 0; i < fields.length; ++i) {
            String fieldName = fields[i].trim();
            fields[i] = tableMapping.toColumnName(entityClass, fieldName);

        }
        return StringUtil.join(fields, ",");
    }

    /**
     * 根据映射信息转换 Where SQL 语句
     */
    public static String transferCondition(Class<?> entityClass, String conditions) {
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isEmpty(conditions)) return sb.toString();

        Matcher matcher = conditionPattern.matcher(conditions.trim());
        while (matcher.find()) {
            String columnName = tableMapping.toColumnName(entityClass, matcher.group(1));
            String operator = matcher.group(2).toUpperCase();
            matcher.appendReplacement(sb, columnName);
            sb.append(" ").append(operator).append(" ");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 根据映射信息转换 Group By SQL 语句
     */
    public static String transferGroupBy(Class<?> entityClass, String groupBy) {
        if (StringUtil.isEmpty(groupBy)) return "";
        String[] fields = groupBy.split(",");
        for (int i = 0; i < fields.length; ++i) {
            String fieldName = fields[i].trim();
            fields[i] = tableMapping.toColumnName(entityClass, fieldName);

        }
        return StringUtil.join(fields);
    }

    /**
     * 根据映射信息转换 Having SQL 语句
     */
    public static String transferHaving(Class<?> entityClass, String groupBy) {
        return null;
    }

    /**
     * 根据映射信息转换 Order BY SQL 语句
     */
    public static String transferOrderBy(Class<?> entityClass, String orderBy) {
        if (StringUtil.isEmpty(orderBy)) return "";

        String[] fields = orderBy.split(",");
        for (int i = 0; i < fields.length; ++i) {
            String[] pair = fields[i].split("\\s+");
            if (CollectionUtil.isEmpty(pair) || pair.length > 2) {
                throw new QueryException("[JFlask] ORDER BY 字句格式错误!");
            }
            String column = tableMapping.toColumnName(entityClass, pair[0].trim());
            String order = pair[1].trim();
            if (!Pattern.compile("^(?i)ASC|DESC$").matcher(order).matches()) {
                throw new QueryException("[JFlask] ORDER BY 字句格式错误!");
            }
            fields[i] = column + " " + order;
        }
        return StringUtil.join(fields);
    }

}
