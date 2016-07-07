package com.rayn.jflask.framework.mvc.model;

/**
 * Result
 * Created by Raynxxx on 2016/05/30.
 */
public class Result {
    private boolean success;
    private int error;
    private Object data;

    public Result(boolean success) {
        this.success = success;
    }

    public Result error(int error) {
        this.error = error;
        return this;
    }

    public Result data(Object data) {
        this.data = data;
        return this;
    }
}
