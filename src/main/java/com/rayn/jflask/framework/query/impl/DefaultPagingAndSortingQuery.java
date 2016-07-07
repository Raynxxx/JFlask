package com.rayn.jflask.framework.query.impl;

import com.rayn.jflask.framework.query.PagingAndSortingQuery;
import com.rayn.jflask.framework.query.model.Page;
import com.rayn.jflask.framework.query.model.PageRequest;
import com.rayn.jflask.framework.query.model.Sort;

import java.io.Serializable;
import java.util.Iterator;

/**
 * DefaultPagingAndSortingQuery
 * Created by Raynxxx on 2016/06/07.
 */
public class DefaultPagingAndSortingQuery<T, ID extends Serializable>
        extends DefaultCurdQuery<T, ID>
        implements PagingAndSortingQuery<T, ID> {

    @Override
    public Page<T> findAll(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Iterator<T> findAll(Sort sort) {
        return null;
    }
}
