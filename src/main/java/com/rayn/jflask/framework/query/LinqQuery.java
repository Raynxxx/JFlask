package com.rayn.jflask.framework.query;

import java.util.List;

/**
 * DefaultLinqQuery
 * Created by Raynxxx on 2016/08/02.
 */
public interface LinqQuery<T> extends Query<T> {

    LinqQuery<T> select(String selectFields);

    LinqQuery<T> where(String conditions, Object... params);

    LinqQuery<T> groupBy(String groupBy);

    LinqQuery<T> having(String having);

    LinqQuery<T> orderBy(String orderBy);

    T first();

    T last();

    List<T> all();

    long count();
}
