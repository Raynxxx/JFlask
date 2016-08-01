package com.rayn.jflask.framework.query.impl;

import com.rayn.jflask.framework.orm.ORMLoader;
import com.rayn.jflask.framework.orm.dialect.Dialect;
import com.rayn.jflask.framework.orm.helper.DatabaseHelper;
import com.rayn.jflask.framework.query.Query;

import java.util.List;

/**
 * DefaultQuery
 * Created by Raynxxx on 2016/07/19.
 */
public class DefaultQuery<T> implements Query<T> {

    private static final Dialect dialect = ORMLoader.getDefaultDialect();

    private final Class<T> entityClass;

    public DefaultQuery(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public <PK> T find(PK primary) {
        String sql = dialect.forSelectById(entityClass);
        return DatabaseHelper.queryEntity(entityClass, sql, primary);
    }

    @Override
    public List<T> findAll() {
        String sql = dialect.forSelect(entityClass, null);
        return DatabaseHelper.queryEntityList(entityClass, sql);
    }

    @Override
    public List<T> findAll(String condition, Object... params) {
        String sql = dialect.forSelect(entityClass, condition);
        return DatabaseHelper.queryEntityList(entityClass, sql, params);
    }
}
