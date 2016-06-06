package org.rayn.jflask.framework.query;

import org.rayn.jflask.framework.query.model.Page;
import org.rayn.jflask.framework.query.model.PageRequest;
import org.rayn.jflask.framework.query.model.Sort;

import java.io.Serializable;
import java.util.Iterator;

/**
 * PagingAndSortingQuery
 * Created by Raynxxx on 2016/06/05.
 */
public interface PagingAndSortingQuery<T, ID extends Serializable>
        extends CurdQuery<T, ID> {

    Page<T> findAll(PageRequest pageRequest);

    Iterator<T> findAll(Sort sort);
}
