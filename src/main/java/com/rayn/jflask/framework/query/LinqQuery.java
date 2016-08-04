package com.rayn.jflask.framework.query;

import java.io.Serializable;
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

    //LinqQuery<T, PK> paginate();

    T first();

    T last();

    List<T> all();

    long count();
}
