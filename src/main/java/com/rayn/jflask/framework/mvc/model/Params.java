package com.rayn.jflask.framework.mvc.model;

import com.rayn.jflask.framework.util.ClassUtil;

import java.util.Map;

/**
 * Params
 * Created by Raynxxx on 2016/05/30.
 */
public class Params {
    private Map<String, Object> requestMap;

    public Params(Map<String, Object> paramsMap) {
        this.requestMap = paramsMap;
    }

    public Map<String, Object> getRequestMap() {
        return requestMap;
    }

    public Object get(String key) {
        return requestMap.get(key);
    }

    public String getString(String key) {
        return ClassUtil.toString(requestMap.get(key));
    }

    public int getInteger(String key) {
        return ClassUtil.toInteger(requestMap.get(key));
    }

    public double getDouble(String key) {
        return ClassUtil.toDouble(requestMap.get(key));
    }

    public long getLong(String key) {
        return ClassUtil.toLong(requestMap.get(key));
    }

}
