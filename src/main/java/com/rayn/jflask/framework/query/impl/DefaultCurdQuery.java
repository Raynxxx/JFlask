package com.rayn.jflask.framework.query.impl;

import com.rayn.jflask.framework.query.CurdQuery;

import java.io.Serializable;
import java.util.Iterator;

/**
 * DefaultCurdQuery
 * Created by Raynxxx on 2016/06/06.
 */
public class DefaultCurdQuery<T, ID extends Serializable>
        implements CurdQuery<T, ID> {

    @Override
    public T save(T entity) {
        return null;
    }

    @Override
    public Iterator<T> save(Iterator<T> entities) {
        return null;
    }

    @Override
    public T findOne(ID key) {
        return null;
    }

    @Override
    public Iterator<T> findAll() {
        return null;
    }

    @Override
    public Iterator<T> findAll(Iterator<ID> ids) {
        return null;
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public void delete(ID key) {

    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void delete(Iterator<T> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean exists(ID key) {
        return false;
    }
}
