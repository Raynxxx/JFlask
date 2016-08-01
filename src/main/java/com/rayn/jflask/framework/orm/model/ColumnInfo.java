package com.rayn.jflask.framework.orm.model;

/**
 * ColumnInfo
 * Created by Raynxxx on 2016/07/19.
 */
public class ColumnInfo {

    private String name;
    private boolean isPrimary;
    private Class<?> columnType;

    public ColumnInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public Class<?> getColumnType() {
        return columnType;
    }

    public void setColumnType(Class<?> columnType) {
        this.columnType = columnType;
    }
}
