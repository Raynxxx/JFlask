package com.rayn.jflask.framework.mvc.result;

/**
 * JsonResult
 * Created by Raynxxx on 2016/07/09.
 */
public class JsonResult extends Result {

    private Object data;

    public JsonResult(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return super.toString() + "<JSON>";
    }
}
