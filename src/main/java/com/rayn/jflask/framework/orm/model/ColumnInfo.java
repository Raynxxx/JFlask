package com.rayn.jflask.framework.orm.model;

/**
 * ColumnInfo
 * Created by Raynxxx on 2016/07/19.
 */
public class ColumnInfo {

    private String name;
    private boolean primary;
    private boolean autoIncrement;
    private boolean canNull;
    private boolean unique;
    private int length;

    public ColumnInfo() {
        this.primary = false;
        this.canNull = false;
        this.unique = false;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCanNull() {
        return canNull;
    }

    public void setCanNull(boolean canNull) {
        this.canNull = canNull;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
