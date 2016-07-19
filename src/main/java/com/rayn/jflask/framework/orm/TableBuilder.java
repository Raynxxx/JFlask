package com.rayn.jflask.framework.orm;

import com.rayn.jflask.framework.annotation.entity.Column;
import com.rayn.jflask.framework.annotation.entity.Table;
import com.rayn.jflask.framework.orm.model.TableInfo;
import com.rayn.jflask.framework.util.CollectionUtil;
import com.rayn.jflask.framework.util.StringUtil;

import java.lang.reflect.Field;

/**
 * TableBuilder
 * Created by Raynxxx on 2016/07/18.
 */
public class TableBuilder {

    /**
     * 从一个 Model 生成 TableInfo 信息
     */
    public static TableInfo parseTable(Class<?> modelClass) {
        TableInfo tableInfo = new TableInfo(modelClass);
        parseTableName(modelClass, tableInfo);
        parseTableField(modelClass, tableInfo);
        return null;
    }

    /**
     * 解析 Table Name
     */
    private static void parseTableName(Class<?> modelClass, TableInfo tableInfo) {
        String tableName = null;
        if (modelClass.isAnnotationPresent(Table.class)) {
            tableName = modelClass.getAnnotation(Table.class).name();
        } else {
            tableName = StringUtil.camelToUnderline(modelClass.getSimpleName());
        }
        tableInfo.setTableName(tableName);
    }

    /**
     * 解析 Table Field
     */
    private static void parseTableField(Class<?> modelClass, TableInfo tableInfo) {
        Field[] tableFields = modelClass.getDeclaredFields();
        if (CollectionUtil.isNotEmpty(tableFields)) {
            for (Field field : tableFields) {
                String fieldName = parseColumnName(field);
            }
        }
        // TODO
    }

    private static String parseColumnName(Field field) {
        return "";
    }
}
