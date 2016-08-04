package com.rayn.jflask.framework.query;

import java.io.Serializable;
import java.util.List;

/**
 * Query
 * Created by Raynxxx on 2016/07/19.
 */
public interface Query<T, PK extends Serializable> {

    PK save(T entity);

    T find(PK primaryKey);

    T findBy(String conditions, Object... params);

    //List<T> findAll(List<PK> selectIds);

    List<T> findAll(String conditions, Object... params);

    boolean exist(String conditions, Object... params);
}
