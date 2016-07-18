package com.rayn.jflask.framework.orm.model;

import java.util.Map;

/**
 * TableInfo
 * Created by Raynxxx on 2016/07/18.
 */
public class TableInfo {

    private Class<?> modelClass;
    private String tableName;
    private Map<String, String> fieldMap;

    public TableInfo(Class<?> modelClass) {
        this.modelClass = modelClass;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, String> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, String> fieldMap) {
        this.fieldMap = fieldMap;
    }
}
