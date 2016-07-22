package com.rayn.jflask.framework.orm;

import com.rayn.jflask.framework.annotation.entity.Column;
import com.rayn.jflask.framework.annotation.entity.Primary;
import com.rayn.jflask.framework.annotation.entity.Table;
import com.rayn.jflask.framework.orm.model.ColumnInfo;
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
     * 解析 Table Field
     */
    private static void parseTableField(Class<?> modelClass, TableInfo tableInfo) {
        Field[] tableFields = modelClass.getDeclaredFields();
        if (CollectionUtil.isNotEmpty(tableFields)) {
            for (Field field : tableFields) {
                String fieldName = field.getName();
                tableInfo.putColumn(fieldName, parseColumnInfo(field));
            }
        }
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

    private static ColumnInfo parseColumnInfo(Field field) {
        ColumnInfo columnInfo = new ColumnInfo();
        // 主键
        if (field.isAnnotationPresent(Primary.class)) {
            columnInfo.setPrimary(true);
        }
        // 建名
        String columnName = StringUtil.camelToUnderline(field.getName());
        if (field.isAnnotationPresent(Column.class)) {
            String declaredName = field.getDeclaredAnnotation(Column.class).name();
            if (StringUtil.isNotEmpty(declaredName)) {
                columnName = declaredName;
            }
        }
        columnInfo.setName(columnName);
        // TODO
        return columnInfo;
    }
}
