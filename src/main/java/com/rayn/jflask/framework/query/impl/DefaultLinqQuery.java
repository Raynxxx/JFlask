package com.rayn.jflask.framework.query.impl;

import com.rayn.jflask.framework.query.QueryProvider;
import com.rayn.jflask.framework.query.LinqQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DefaultLinqQuery
 * Created by Raynxxx on 2016/08/02.
 */
public class DefaultLinqQuery<T> extends DefaultQuery<T>
        implements LinqQuery<T> {

    private String fullSql;
    private List<Object> paramsList;

    public DefaultLinqQuery(Class<T> entityClass) {
        super(entityClass);
        this.fullSql = null;
        this.paramsList = new ArrayList<>();
    }

    @Override
    public LinqQuery<T> select(String selectFields) {
        this.fullSql = dialect.forSelect(entityClass, selectFields);
        return this;
    }

    @Override
    public LinqQuery<T> where(String conditions, Object... params) {
        if (this.fullSql == null) {
            this.fullSql = dialect.forSelectWhere(entityClass, conditions);
        } else {
            this.fullSql += dialect.generateWhere(entityClass, conditions);
        }
        Collections.addAll(this.paramsList, params);
        return this;
    }

    @Override
    public LinqQuery<T> groupBy(String groupBy) {
        if (this.fullSql == null) {
            this.fullSql = dialect.forSelectGroupBy(entityClass, groupBy);
        } else {
            this.fullSql += dialect.generateGroupBy(entityClass, groupBy);
        }
        return this;
    }

    @Override
    public LinqQuery<T> having(String having) {
        this.fullSql += dialect.generateHaving(entityClass, having);
        return this;
    }

    @Override
    public LinqQuery<T> orderBy(String orderBy) {
        if (this.fullSql == null) {
            this.fullSql = dialect.forSelectOrderBy(entityClass, orderBy);
        } else {
            this.fullSql += dialect.generateOrderBy(entityClass, orderBy);
        }
        return this;
    }

    @Override
    public T first() {
        T ret;
        if (this.fullSql == null) {
            this.fullSql = dialect.forSelectFirst(entityClass);
            ret = QueryProvider.queryEntity(entityClass, this.fullSql);
        } else if (this.fullSql.contains("ORDER BY")) {
            this.fullSql += " LIMIT 1";
            ret = QueryProvider.queryEntity(entityClass, this.fullSql, this.paramsList.toArray());
        } else {
            this.fullSql += dialect.generateSelectFirst(entityClass);
            ret = QueryProvider.queryEntity(entityClass, this.fullSql, this.paramsList.toArray());
        }
        this.reset();
        return ret;
    }

    @Override
    public T last() {
        T ret;
        if (this.fullSql == null) {
            this.fullSql = dialect.forSelectLast(entityClass);
            ret = QueryProvider.queryEntity(entityClass, this.fullSql);
        } else if (this.fullSql.contains("ORDER BY")) {
            this.fullSql += " LIMIT 1";
            ret = QueryProvider.queryEntity(entityClass, this.fullSql, this.paramsList.toArray());
        } else {
            this.fullSql += dialect.generateSelectLast(entityClass);
            ret = QueryProvider.queryEntity(entityClass, this.fullSql, this.paramsList.toArray());
        }
        this.reset();
        return ret;
    }

    @Override
    public List<T> all() {
        List<T> ret;
        if (this.fullSql == null) {
            this.fullSql = dialect.forSelect(entityClass, "");
            ret = QueryProvider.queryEntityList(entityClass, this.fullSql);
        } else {
            ret = QueryProvider.queryEntityList(entityClass, this.fullSql, this.paramsList.toArray());
        }
        this.reset();
        return ret;
    }

    @Override
    public long count() {
        long ret;
        if (this.fullSql == null) {
            this.fullSql = dialect.forSelectCount(entityClass, "");
            ret = QueryProvider.queryCount(this.fullSql);
        } else {
            ret = QueryProvider.queryCount(this.fullSql, this.paramsList.toArray());
        }
        return ret;
    }

    private void reset() {
        this.fullSql = null;
        this.paramsList.clear();
    }
}
