package org.rayn.jflask.framework.orm;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.rayn.jflask.framework.query.Query;
import org.rayn.jflask.framework.query.impl.DefaultCurdQuery;

import java.io.Serializable;
import java.util.HashMap;

/**
 * BaseEntity
 * Created by Raynxxx on 2016/06/05.
 */
public abstract class BaseEntity<T, ID extends Serializable>
        implements Serializable {

    private final Query<T, ID> entityQuery = new DefaultCurdQuery<T, ID>();

    public Query<T, ID> query() {
        return this.entityQuery;
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
