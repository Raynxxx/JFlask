package com.rayn.jflask.framework.orm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TableInfo
 * Created by Raynxxx on 2016/07/18.
 */
public class TableInfo {

    // table name
    private String tableName;

    // model class
    private Class<?> modelClass;

    // priamry key
    private String primaryKey;

    // field
    private Map<String, ColumnInfo> fieldToColumnMap;

    public TableInfo(Class<?> modelClass) {
        this.modelClass = modelClass;
        fieldToColumnMap = new HashMap<>();
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
}
