package com.rayn.jflask.framework.mvc.result;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * Result
 * Created by Raynxxx on 2016/05/30.
 */
public abstract class Result implements Serializable {
    // logger
    protected static final Logger logger = LoggerFactory.getLogger(Result.class);

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
        return "[Respond Result]";
    }

    public abstract void render(HttpServletRequest request,
                                HttpServletResponse response) throws Throwable;
}
