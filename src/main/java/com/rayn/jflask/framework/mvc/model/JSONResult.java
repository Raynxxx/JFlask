package com.rayn.jflask.framework.mvc.model;

/**
 * JSONResult
 * Created by Raynxxx on 2016/07/09.
 */
public class JSONResult extends Result {
    public static final boolean OK = true;
    public static final boolean FAIL = false;

    private boolean status;
    private String message;
    private Object data;

    public JSONResult(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public JSONResult setData(Object data) {
        this.data = data;
        return this;
    }
}
