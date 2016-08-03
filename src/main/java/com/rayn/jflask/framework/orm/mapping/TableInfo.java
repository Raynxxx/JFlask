package com.rayn.jflask.framework.orm.mapping;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TableInfo
 * Created by Raynxxx on 2016/07/18.
 */
public class TableInfo {

    // table name
    private String tableName;

    // mapping class
    private Class<?> modelClass;

    // primary key
    private String primaryKey;

    // fieldName => Column
    private Map<String, ColumnInfo> fieldToColumnMap;

    // fieldName => ColumnName
    private Map<String, String> fieldToColumnNameMap;

    public TableInfo(Class<?> modelClass) {
        this.modelClass = modelClass;
        fieldToColumnMap = new LinkedHashMap<>();
    }

    public Class<?> getModelClass() {
        return modelClass;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void putColumn(String key, ColumnInfo columnInfo) {
        fieldToColumnMap.put(key, columnInfo);
    }

    public Map<String, ColumnInfo> getColumnInfoMap() {
        return fieldToColumnMap;
    }

    public Map<String, String> getFieldToColumnNameMap() {
        return fieldToColumnNameMap;
    }

    public void setFieldToColumnNameMap(Map<String, String> fieldToColumnNameMap) {
        this.fieldToColumnNameMap = fieldToColumnNameMap;
    }
}
