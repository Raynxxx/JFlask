package com.rayn.jflask.framework.query;

import java.io.Serializable;
import java.util.List;

/**
 * Query
 * Created by Raynxxx on 2016/07/19.
 */
public interface Query<T, PK extends Serializable> {

    PK save(T entity);

    Iterable<PK> save(Iterable<T> entities);

    T find(PK primaryKey);

    T findBy(String conditions, Object... params);

    Iterable<T> findAll(List<PK> selectIds);

    Iterable<T> findAll(String conditions, Object... params);

    boolean exist(String conditions, Object... params);

    void delete(PK primaryKey);

    void delete(T entity);

    void deleteAll(Iterable<T> entities);
}
