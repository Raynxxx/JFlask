package com.rayn.jflask.framework.mvc.result;

import com.rayn.jflask.framework.mvc.helper.ServletHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response)
            throws Throwable {
        ServletHelper.responseJSON(response, this.getData());
    }
}
