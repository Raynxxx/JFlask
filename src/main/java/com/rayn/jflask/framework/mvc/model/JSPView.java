package com.rayn.jflask.framework.mvc.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * JSPView
 * Created by Raynxxx on 2016/05/30.
 */
public class JSPView implements Serializable {
    private String path;
    private Map<String, Object> data;

    public JSPView(String path) {
        this.path = path;
        data = new HashMap<String, Object>();
    }

    public JSPView put(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public boolean isRedirect() {
        return this.path.startsWith("/");
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
}
