package com.rayn.jflask.framework.orm;

import com.rayn.jflask.framework.orm.model.BaseModel;
import com.rayn.jflask.framework.orm.model.ColumnInfo;
import com.rayn.jflask.framework.orm.model.TableInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * TableMapping
 * Created by Raynxxx on 2016/07/23.
 */
public class TableMapping {

    private final Map<Class<?>, TableInfo> modelToTableMap = new HashMap<>();

    private static TableMapping me = new TableMapping();

    public static TableMapping me() {
        return me;
    }

    public void putTable(TableInfo table) {
        modelToTableMap.put(table.getModelClass(), table);
    }

    public TableInfo getTable(Class<?> modelClass) {
        return modelToTableMap.get(modelClass);
    }

    public Map<String, String> getColumnMap(Class<?> modelClass) {
        TableInfo tableInfo = getTable(modelClass);
        Map<String, String> columnMap = new HashMap<>();
        if (tableInfo != null) {
            Map<String, ColumnInfo> columnInfoMap = tableInfo.getColumnInfoMap();
            for (Map.Entry<String, ColumnInfo> entry : columnInfoMap.entrySet()) {
                columnMap.put(entry.getKey(), entry.getValue().getName());
            }
        }
        return columnMap;
    }
}
