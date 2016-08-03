package com.rayn.jflask.framework.query.impl;

import com.rayn.jflask.framework.orm.helper.DatabaseHelper;
import com.rayn.jflask.framework.orm.helper.SqlHelper;
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

    private ThreadLocal<String> fullSql;
    private ThreadLocal<List<Object>> paramsList;

    public DefaultLinqQuery(Class<T> entityClass) {
        super(entityClass);
        this.fullSql = new ThreadLocal<>();
        this.paramsList = new ThreadLocal<>();
    }

    @Override
    public LinqQuery<T> select(String selectFields) {
        this.fullSql.set(dialect.forSelect(entityClass, selectFields));
        return this;
    }

    @Override
    public LinqQuery<T> where(String conditions, Object... params) {
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelectWhere(entityClass, conditions));
        } else {
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateWhere(entityClass, conditions));
        }
        if (this.paramsList.get() == null) {
            this.paramsList.set(new ArrayList<>());
        }
        Collections.addAll(this.paramsList.get(), params);
        return this;
    }

    @Override
    public LinqQuery<T> groupBy(String groupBy) {
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelectGroupBy(entityClass, groupBy));
        } else {
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateGroupBy(entityClass, groupBy));
        }
        return this;
    }

    @Override
    public LinqQuery<T> having(String having) {
        this.fullSql.set(this.fullSql.get() + dialect.generateHaving(entityClass, having));
        return this;
    }

    @Override
    public LinqQuery<T> orderBy(String orderBy) {
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelectOrderBy(entityClass, orderBy));
        } else {
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateOrderBy(entityClass, orderBy));
        }
        return this;
    }

    @Override
    public T first() {
        T ret;
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelectFirst(entityClass));
            ret = QueryProvider.queryEntity(entityClass, this.fullSql.get());
        } else if (this.fullSql.get().contains("ORDER BY")) {
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateSelectFirst(entityClass, false));
            ret = QueryProvider.queryEntity(entityClass, this.fullSql.get(),
                    this.paramsList.get().toArray());
        } else {
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateSelectFirst(entityClass, true));
            ret = QueryProvider.queryEntity(entityClass, this.fullSql.get(),
                    this.paramsList.get().toArray());
        }
        this.reset();
        return ret;
    }

    @Override
    public T last() {
        T ret;
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelectLast(entityClass));
            ret = QueryProvider.queryEntity(entityClass, this.fullSql.get());
        } else if (this.fullSql.get().contains("ORDER BY")) {
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateSelectLast(entityClass, false));
            ret = QueryProvider.queryEntity(entityClass, this.fullSql.get(),
                    this.paramsList.get().toArray());
        } else {
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateSelectLast(entityClass, true));
            ret = QueryProvider.queryEntity(entityClass, this.fullSql.get(),
                    this.paramsList.get().toArray());
        }
        this.reset();
        return ret;
    }

    @Override
    public List<T> all() {
        List<T> ret;
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelect(entityClass, ""));
            ret = QueryProvider.queryEntityList(entityClass, this.fullSql.get());
        } else {
            ret = QueryProvider.queryEntityList(entityClass, this.fullSql.get(),
                    this.paramsList.get().toArray());
        }
        this.reset();
        return ret;
    }

    @Override
    public long count() {
        long ret;
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelectCount(entityClass, ""));
            ret = QueryProvider.queryCount(this.fullSql.get());
        } else {
            ret = QueryProvider.queryCount(this.fullSql.get(),
                    this.paramsList.get().toArray());
        }
        this.reset();
        return ret;
    }

    private void reset() {
        this.fullSql.remove();
        this.paramsList.remove();
    }
}
