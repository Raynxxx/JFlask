package com.rayn.jflask.framework.mvc.result;

import java.util.HashMap;
import java.util.Map;

/**
 * JspResult
 * Created by Raynxxx on 2016/05/30.
 */
public class JspResult extends Result {
    private String path;
    private Map<String, Object> data;

    public JspResult(String path) {
        this.path = path;
        data = new HashMap<>();
    }

    public JspResult put(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return super.toString() + "<JSP>";
    }
}
