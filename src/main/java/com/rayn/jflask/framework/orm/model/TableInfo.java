package com.rayn.jflask.framework.orm.model;

import java.util.HashMap;
import java.util.Map;

/**
 * TableInfo
 * Created by Raynxxx on 2016/07/18.
 */
public class TableInfo {

    // model class
    private Class<? extends BaseModel<?>> modelClass;

    // table name
    private String tableName;

    // field
    private Map<String, ColumnInfo> fieldToColumnMap;

    public TableInfo(Class<? extends BaseModel<?>> modelClass) {
        this.modelClass = modelClass;
        fieldToColumnMap = new HashMap<>();
    }

    public Class<? extends BaseModel<?>> getModelClass() {
        return modelClass;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void putColumn(String key, ColumnInfo columnInfo) {
        fieldToColumnMap.put(key, columnInfo);
    }
}
