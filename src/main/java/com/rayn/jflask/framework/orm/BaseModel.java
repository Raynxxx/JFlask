package com.rayn.jflask.framework.orm;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * BaseModel
 * Created by Raynxxx on 2016/07/18.
 */
public class BaseModel<T> implements Serializable {

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
        return String.format("<Model \"%s\">", this.getSubClassType().getSimpleName());
    }

    @SuppressWarnings("unchecked")
    public Class<T> getSubClassType() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] params = type.getActualTypeArguments();
        return (Class<T>) params[0];
    }

    public static void create() {

    }

    public void save() {

    }

    public void destroy() {

    }
}
