package com.rayn.jflask.framework.routing.request;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;

/**
 * StaticRequest
 * 封装 StaticRequest 信息
 * Created by Raynxxx on 2016/05/14.
 */
public class StaticRequest implements Serializable, Request {
    private final String method;
    private final String path;

    public StaticRequest(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return String.format("StaticRequest => %s:%s", this.method, this.path);
    }
}
