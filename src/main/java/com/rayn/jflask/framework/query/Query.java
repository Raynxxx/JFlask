package com.rayn.jflask.framework.query;

/**
 * Query
 * Created by Raynxxx on 2016/07/19.
 */
public interface Query<T> {

    <PK> T find(PK primaryKey);

    <PK> PK save(T entity);

    boolean exist(String conditions);
}
