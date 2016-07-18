package com.rayn.jflask.framework.orm;

import com.rayn.jflask.framework.annotation.entity.Table;
import com.rayn.jflask.framework.orm.model.TableInfo;
import com.rayn.jflask.framework.util.StringUtil;

/**
 * TableBuilder
 * Created by Raynxxx on 2016/07/18.
 */
public class TableBuilder {

    /**
     * 从一个 Model 生成 TableInfo 信息
     */
    public static TableInfo makeTable(Class<?> modelClass) {
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
        // TODO
    }
}
