package com.rayn.jflask.framework.orm;

import com.rayn.jflask.framework.annotation.entity.Column;
import com.rayn.jflask.framework.annotation.entity.Primary;
import com.rayn.jflask.framework.annotation.entity.Table;
import com.rayn.jflask.framework.orm.model.BaseModel;
import com.rayn.jflask.framework.orm.model.ColumnInfo;
import com.rayn.jflask.framework.orm.model.TableInfo;
import com.rayn.jflask.framework.util.CollectionUtil;
import com.rayn.jflask.framework.util.StringUtil;

import java.lang.reflect.Field;
import java.util.List;

/**
 * TableBuilder
 * Created by Raynxxx on 2016/07/18.
 */
public class TableBuilder {

    /**
     * 构建 Model
     */
    public static void build(List<Class<?>> modelList) {
        for (Class<?> model : modelList) {
            parseTable(model);
        }
    }

    /**
     * 从一个 Model 生成 TableInfo 信息
     */
    public static TableInfo parseTable(Class<?> modelClass) {
        TableInfo tableInfo = new TableInfo(modelClass);
        tableInfo.setTableName(parseTableName(modelClass));
        parseTableField(modelClass, tableInfo);
        return null;
    }

    /**
     * 解析 Table Name
     */
    private static String parseTableName(Class<?> modelClass) {
        String tableName;
        if (modelClass.isAnnotationPresent(Table.class)) {
            tableName = modelClass.getAnnotation(Table.class).name();
        } else {
            tableName = StringUtil.camelToUnderline(modelClass.getSimpleName());
        }
        return tableName;

    }

    /**
     * 解析 Table Field
     */
    private static void parseTableField(Class<?> modelClass, TableInfo tableInfo) {
        Field[] tableFields = modelClass.getDeclaredFields();
        if (CollectionUtil.isNotEmpty(tableFields)) {
            for (Field field : tableFields) {
                String fieldName = field.getName();
                ColumnInfo columnInfo = parseColumnInfo(field);
                if (columnInfo != null) {
                    tableInfo.putColumn(fieldName, parseColumnInfo(field));
                }
            }
        }
    }


    private static ColumnInfo parseColumnInfo(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return null;
        }
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        ColumnInfo columnInfo = new ColumnInfo();

        // 键名
        String columnName = columnAnnotation.name();
        if (StringUtil.isNotEmpty(columnName)) {
            columnName = StringUtil.camelToUnderline(field.getName());
        }
        columnInfo.setName(columnName);

        // 主键
        if (field.isAnnotationPresent(Primary.class)) {
            columnInfo.setPrimary(true);
            columnInfo.setAutoIncrement(field.getAnnotation(Primary.class)
                    .autoIncrement());
        }

        // can null
        columnInfo.setCanNull(columnAnnotation.canNull());

        // unique
        columnInfo.setUnique(columnAnnotation.unique());

        // length
        columnInfo.setLength(columnAnnotation.length());

        return columnInfo;
    }
}
