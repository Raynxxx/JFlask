package com.rayn.jflask.framework.query.impl;

import com.rayn.jflask.framework.orm.helper.SqlHelper;
import com.rayn.jflask.framework.query.QueryProvider;
import com.rayn.jflask.framework.query.LinqQuery;
import com.rayn.jflask.framework.util.CollectionUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DefaultLinqQuery
 * Created by Raynxxx on 2016/08/02.
 */
public class DefaultLinqQuery<T, PK extends Serializable>
        extends DefaultQuery<T, PK>
        implements LinqQuery<T, PK> {

    private ThreadLocal<String> fullSql;
    private ThreadLocal<List<Object>> paramsList;

    public DefaultLinqQuery(Class<T> entityClass) {
        super(entityClass);
        this.fullSql = new ThreadLocal<>();
        this.paramsList = new ThreadLocal<>();
    }

    @Override
    public LinqQuery<T, PK> select(String selectFields) {
        this.fullSql.set(dialect.forSelect(entityClass, selectFields));
        SqlHelper.outputSQL(this.fullSql.get());
        return this;
    }

    @Override
    public LinqQuery<T, PK> where(String conditions, Object... params) {
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelectWhere(entityClass, conditions, params));
        } else {
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateWhere(entityClass, conditions, params));
        }
        SqlHelper.outputSQL(this.fullSql.get());
        if (this.paramsList.get() == null) {
            this.paramsList.set(new ArrayList<>());
        }
        Collections.addAll(this.paramsList.get(), params);
        return this;
    }

    @Override
    public LinqQuery<T, PK> groupBy(String groupBy) {
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelectGroupBy(entityClass, groupBy));
        } else {
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateGroupBy(entityClass, groupBy));
        }
        SqlHelper.outputSQL(this.fullSql.get());
        return this;
    }

    @Override
    public LinqQuery<T, PK> having(String having) {
        this.fullSql.set(this.fullSql.get() + dialect.generateHaving(entityClass, having));
        SqlHelper.outputSQL(this.fullSql.get());
        return this;
    }

    @Override
    public LinqQuery<T, PK> orderBy(String orderBy) {
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelectOrderBy(entityClass, orderBy));
        } else {
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateOrderBy(entityClass, orderBy));
        }
        SqlHelper.outputSQL(this.fullSql.get());
        return this;
    }

    @Override
    public LinqQuery<T, PK> limit(long size) {
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelect(entityClass, ""));
        } else {
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateLimit(size));
        }
        SqlHelper.outputSQL(this.fullSql.get());
        return this;
    }

    @Override
    public LinqQuery<T, PK> offset(long size) {
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelect(entityClass, ""));
        } else {
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateOffset(size));
        }
        SqlHelper.outputSQL(this.fullSql.get());
        return this;
    }

    @Override
    public T first() {
        T ret;
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelectFirst(entityClass, 1));
        } else {
            boolean needOrder = !this.fullSql.get().contains("ORDER BY");
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateSelectFirst(entityClass, needOrder, 1));
        }
        if (CollectionUtil.isEmpty(this.paramsList.get())) {
            ret = QueryProvider.queryEntity(entityClass, this.fullSql.get());
        } else {
            ret = QueryProvider.queryEntity(entityClass, this.fullSql.get(),
                    this.paramsList.get().toArray());
        }
        SqlHelper.outputSQL(this.fullSql.get());
        this.reset();
        return ret;
    }

    @Override
    public T last() {
        T ret;
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelectLast(entityClass, 1));
        } else {
            boolean needOrder = !this.fullSql.get().contains("ORDER BY");
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateSelectLast(entityClass, needOrder, 1));
        }
        if (CollectionUtil.isEmpty(this.paramsList.get())) {
            ret = QueryProvider.queryEntity(entityClass, this.fullSql.get());
        } else {
            ret = QueryProvider.queryEntity(entityClass, this.fullSql.get(),
                    this.paramsList.get().toArray());
        }
        SqlHelper.outputSQL(this.fullSql.get());
        this.reset();
        return ret;
    }

    @Override
    public Iterable<T> first(long size) {
        Iterable<T> ret;
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelectFirst(entityClass, size));
        } else {
            boolean needOrder = !this.fullSql.get().contains("ORDER BY");
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateSelectFirst(entityClass, needOrder, size));
        }
        if (CollectionUtil.isEmpty(this.paramsList.get())) {
            ret = QueryProvider.queryEntityList(entityClass, this.fullSql.get());
        } else {
            ret = QueryProvider.queryEntityList(entityClass, this.fullSql.get(),
                    this.paramsList.get().toArray());
        }
        SqlHelper.outputSQL(this.fullSql.get());
        this.reset();
        return ret;
    }

    @Override
    public Iterable<T> last(long size) {
        Iterable<T> ret;
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelectLast(entityClass, size));
        } else {
            boolean needOrder = !this.fullSql.get().contains("ORDER BY");
            this.fullSql.set(this.fullSql.get() +
                    dialect.generateSelectLast(entityClass, needOrder, size));
        }
        if (CollectionUtil.isEmpty(this.paramsList.get())) {
            ret = QueryProvider.queryEntityList(entityClass, this.fullSql.get());
        } else {
            ret = QueryProvider.queryEntityList(entityClass, this.fullSql.get(),
                    this.paramsList.get().toArray());
        }
        SqlHelper.outputSQL(this.fullSql.get());
        this.reset();
        return ret;
    }

    @Override
    public Iterable<T> all() {
        Iterable<T> ret;
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelect(entityClass, ""));
        }
        if (CollectionUtil.isEmpty(this.paramsList.get())) {
            ret = QueryProvider.queryEntityList(entityClass, this.fullSql.get());
        } else {
            ret = QueryProvider.queryEntityList(entityClass, this.fullSql.get(),
                    this.paramsList.get().toArray());
        }
        SqlHelper.outputSQL(this.fullSql.get());
        this.reset();
        return ret;
    }

    @Override
    public long count() {
        long ret;
        if (this.fullSql.get() == null) {
            this.fullSql.set(dialect.forSelectCount(entityClass, ""));
        }
        if (CollectionUtil.isEmpty(this.paramsList.get())) {
            ret = QueryProvider.queryCount(this.fullSql.get());
        } else {
            ret = QueryProvider.queryCount(this.fullSql.get(),
                    this.paramsList.get().toArray());
        }
        SqlHelper.outputSQL(this.fullSql.get());
        this.reset();
        return ret;
    }


    private void reset() {
        this.fullSql.remove();
        this.paramsList.remove();
    }
}
