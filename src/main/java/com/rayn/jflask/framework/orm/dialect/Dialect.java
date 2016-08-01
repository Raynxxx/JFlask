package com.rayn.jflask.framework.orm.dialect;

/**
 * Dialect
 * Created by Raynxxx on 2016/08/01.
 */
public interface Dialect {

    String forSelectById(Class<?> entity);

    String forSelect(Class<?> entity, String conditions);

}
