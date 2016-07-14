package com.rayn.jflask.framework.mvc.model;

/**
 * JSONResult
 * Created by Raynxxx on 2016/07/09.
 */
public class JSONResult extends Result {

    private Object data;

    public JSONResult(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
