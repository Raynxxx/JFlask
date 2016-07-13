package com.rayn.jflask.framework.routing.request;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * DynamicRequest
 * Created by Raynxxx on 2016/07/13.
 */
public class DynamicRequest implements Serializable, Request {
    private final String method;
    private final Pattern pathPattern;

    public DynamicRequest(String method, Pattern pathPattern) {
        this.method = method;
        this.pathPattern = pathPattern;
    }

    public String getMethod() {
        return method;
    }

    public Pattern getPathPattern() {
        return pathPattern;
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
        return String.format("DynamicRequest => %s:%s", this.method, this.pathPattern);
    }
}
