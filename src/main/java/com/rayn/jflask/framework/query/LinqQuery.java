package com.rayn.jflask.framework.query;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * DefaultLinqQuery
 * Created by Raynxxx on 2016/08/02.
 */
public interface LinqQuery<T, PK extends Serializable> extends Query<T, PK> {

    LinqQuery<T, PK> select(String selectFields);

    LinqQuery<T, PK> where(String conditions, Object... params);

    LinqQuery<T, PK> groupBy(String groupBy);

    LinqQuery<T, PK> having(String having);

    LinqQuery<T, PK> orderBy(String orderBy);

    LinqQuery<T, PK> limit(long size);

    LinqQuery<T, PK> offset(long size);

    T first();

    T last();

    Iterable<T> first(long size);

    Iterable<T> last(long size);

    Iterable<T> all();

    long count();
}
