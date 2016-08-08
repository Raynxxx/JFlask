package com.rayn.jflask.framework.query.impl;

import com.rayn.jflask.framework.orm.ActiveRecordInitializer;
import com.rayn.jflask.framework.orm.helper.SqlHelper;
import com.rayn.jflask.framework.query.QueryProvider;
import com.rayn.jflask.framework.orm.dialect.Dialect;
import com.rayn.jflask.framework.query.Query;

import java.io.Serializable;
import java.util.List;

/**
 * DefaultCurdQuery
 * Created by Raynxxx on 2016/08/02.
 */
public class DefaultQuery<T, PK extends Serializable> implements Query<T, PK> {

    // dialect
    protected static final Dialect dialect = ActiveRecordInitializer.getDefaultDialect();

    // entityClass
    protected final Class<T> entityClass;

    public DefaultQuery(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public PK save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("[JFlask] entity to SAVE can not be NULL!");
        }
        String sql = dialect.forInsert(entityClass, true);
        Object[] values = SqlHelper.transferModelValues(entityClass, entity, true);
        return QueryProvider.insertEntity(sql, values);
    }

    @Override
    public Iterable<PK> save(Iterable<T> entities) {
        return null;
    }

    @Override
    public T find(PK primaryKey) {
        StringBuffer sb = new StringBuffer(dialect.forSelectByPrimaryKey(entityClass));
        sb.append(dialect.generateSelectFirst(entityClass, false, 1));
        return QueryProvider.queryEntity(entityClass, sb.toString(), primaryKey);
    }

    @Override
    public T findBy(String conditions, Object... params) {
        StringBuffer sb = new StringBuffer(dialect.forSelectWhere(entityClass, conditions));
        sb.append(dialect.generateSelectFirst(entityClass, true, 1));
        return QueryProvider.queryEntity(entityClass, sb.toString(), params);
    }

    @Override
    public Iterable<T> findAll(List<PK> selectIds) {
        // TODO
        return null;
    }

    @Override
    public Iterable<T> findAll(String conditions, Object... params) {
        StringBuffer sb = new StringBuffer(dialect.forSelectWhere(entityClass, conditions));
        return QueryProvider.queryEntityList(entityClass, sb.toString(), params);
    }

    @Override
    public boolean exist(String conditions, Object... params) {
        String sql = dialect.forExists(entityClass, conditions);
        return QueryProvider.queryExists(sql, params);
    }

    @Override
    public void delete(PK primaryKey) {
        String primaryKeyColumn = SqlHelper.getPrimaryKeyColumn(entityClass);
        String sql = dialect.forDelete(entityClass, primaryKeyColumn + " = ?");
    }

    @Override
    public void delete(T entity) {
        String primaryKeyColumn = SqlHelper.getPrimaryKeyColumn(entityClass);
        String sql = dialect.forDelete(entityClass, primaryKeyColumn + " = ?");
    }

    @Override
    public void deleteAll(Iterable<T> entities) {
        // TODO

    }
}
