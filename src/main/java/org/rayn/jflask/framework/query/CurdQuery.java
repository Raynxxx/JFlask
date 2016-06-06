package org.rayn.jflask.framework.query;

import java.io.Serializable;
import java.util.Iterator;

/**
 * CurdQuery
 * Created by Raynxxx on 2016/06/05.
 */
public interface CurdQuery<T, ID extends Serializable>
        extends Query<T, ID> {

    T save(T entity);

    Iterator<T> save(Iterator<T> entities);

    T findOne(ID key);

    Iterator<T> findAll();

    Iterator<T> findAll(Iterator<ID> ids);

    Long count();

    void delete(ID key);

    void delete(T entity);

    void delete(Iterator<T> entities);

    void deleteAll();

    boolean exists(ID key);


}
