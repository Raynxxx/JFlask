package com.rayn.jflask.framework.orm.mapping;

import com.rayn.jflask.framework.core.exception.QueryException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TableMapping
 * Created by Raynxxx on 2016/07/23.
 */
public class TableMapping {

    private final Map<Class<?>, TableInfo> modelToTableMap = new LinkedHashMap<>();

    private static TableMapping me = new TableMapping();

    public static TableMapping me() {
        return me;
    }

    public void putTable(TableInfo table) {
        modelToTableMap.put(table.getModelClass(), table);
    }

    public TableInfo getTableInfo(Class<?> modelClass) {
        if (!modelToTableMap.containsKey(modelClass)) {
            throw new QueryException(String.format("[JFlask] %s is not a Table",
                    modelClass.getSimpleName()));
        }
        return modelToTableMap.get(modelClass);
    }

    public String getTableName(Class<?> modelClass) {
        return getTableInfo(modelClass).getTableName();
    }

    public Map<String, ColumnInfo> getColumnInfoMap(Class<?> modelClass) {
        return getTableInfo(modelClass).getColumnInfoMap();
    }

    public Map<String, String> getColumnMap(Class<?> modelClass) {
        TableInfo tableInfo = getTableInfo(modelClass);
        if (tableInfo.getFieldToColumnNameMap() == null) {
            Map<String, String> columnMap = new LinkedHashMap<>();
            Map<String, ColumnInfo> columnInfoMap = tableInfo.getColumnInfoMap();
            for (Map.Entry<String, ColumnInfo> entry : columnInfoMap.entrySet()) {
                columnMap.put(entry.getKey(), entry.getValue().getName());
            }
            tableInfo.setFieldToColumnNameMap(columnMap);
        }
        return tableInfo.getFieldToColumnNameMap();
    }


    public String toColumnName(Class<?> modelClass, String fieldName) {
        TableInfo tableInfo = getTableInfo(modelClass);
        Map<String, ColumnInfo> columnInfoMap = tableInfo.getColumnInfoMap();
        if (!columnInfoMap.containsKey(fieldName)) {
            throw new QueryException(String.format("[JFlask] %s has not field named %s",
                    modelClass.getSimpleName(), fieldName));
        }
        return columnInfoMap.get(fieldName).getName();
    }
}
