package com.rayn.jflask.framework.orm;

import com.rayn.jflask.framework.annotation.entity.Column;
import com.rayn.jflask.framework.annotation.entity.Table;
import com.rayn.jflask.framework.orm.dialect.Dialect;
import com.rayn.jflask.framework.orm.model.ColumnInfo;
import com.rayn.jflask.framework.orm.model.TableInfo;
import com.rayn.jflask.framework.util.CollectionUtil;
import com.rayn.jflask.framework.util.StringUtil;
import javafx.scene.control.Tab;

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
    public static void build(List<Class<?>> modelList, Dialect dialect) {
        for (Class<?> model : modelList) {
            TableInfo tableInfo = parseTable(model, dialect);
            TableMapping.me().putTable(tableInfo);
        }
    }

    /**
     * 从一个 Model 生成 TableInfo 信息
     */
    public static TableInfo parseTable(Class<?> modelClass, Dialect dialect) {
        TableInfo tableInfo = new TableInfo(modelClass);
        tableInfo.setTableName(parseTableName(modelClass));

        // parse table fields
        Field[] tableFields = modelClass.getDeclaredFields();
        if (CollectionUtil.isNotEmpty(tableFields)) {
            for (Field field : tableFields) {
                if (!field.isAnnotationPresent(Column.class)) continue;

                String fieldName = field.getName();
                ColumnInfo columnInfo = parseColumnInfo(field);
                tableInfo.putColumn(fieldName, columnInfo);

                // set primary key
                if (columnInfo.isPrimary()) {
                    tableInfo.setPrimaryKey(fieldName);
                }
            }
        }
        return tableInfo;
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
     * 解析 Column 的信息
     */
    private static ColumnInfo parseColumnInfo(Field field) {
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        ColumnInfo columnInfo = new ColumnInfo();

        // 键名
        String columnName = columnAnnotation.name();
        if (StringUtil.isNotEmpty(columnName)) {
            columnName = StringUtil.camelToUnderline(field.getName());
        }
        columnInfo.setName(columnName);
        columnInfo.setPrimary(columnAnnotation.isPrimary());

        return columnInfo;
    }
}
