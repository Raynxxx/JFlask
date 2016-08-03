package com.rayn.jflask.framework.query.impl;

import com.rayn.jflask.framework.orm.ORMLoader;
import com.rayn.jflask.framework.query.QueryProvider;
import com.rayn.jflask.framework.orm.dialect.Dialect;
import com.rayn.jflask.framework.query.Query;

/**
 * DefaultCurdQuery
 * Created by Raynxxx on 2016/08/02.
 */
public class DefaultQuery<T> implements Query<T> {

    protected static final Dialect dialect = ORMLoader.getDefaultDialect();

    protected final Class<T> entityClass;

    public DefaultQuery(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public <PK> T find(PK primaryKey) {
        StringBuffer sb = new StringBuffer(dialect.forSelectByPrimaryKey(entityClass));
        sb.append(dialect.generateSelectFirst(entityClass, false));
        return QueryProvider.queryEntity(entityClass, sb.toString(), primaryKey);
    }

    @Override
    public boolean exist(String conditions) {
        return false;
    }
}
