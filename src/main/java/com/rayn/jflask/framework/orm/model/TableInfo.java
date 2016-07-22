package com.rayn.jflask.framework.orm.model;

import com.rayn.jflask.framework.annotation.entity.Column;

import java.util.HashMap;
import java.util.Map;

/**
 * TableInfo
 * Created by Raynxxx on 2016/07/18.
 */
public class TableInfo {

    private Class<?> modelClass;
    private String tableName;
    private Map<String, ColumnInfo> fieldMap;

    public TableInfo(Class<?> modelClass) {
        this.modelClass = modelClass;
        fieldMap = new HashMap<>();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void putColumn(String key, ColumnInfo columnInfo) {
        fieldMap.put(key, columnInfo);
    }
}
