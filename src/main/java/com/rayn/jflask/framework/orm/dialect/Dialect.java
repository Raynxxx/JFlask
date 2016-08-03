package com.rayn.jflask.framework.orm.dialect;


/**
 * Dialect
 * Created by Raynxxx on 2016/08/01.
 */
public interface Dialect {

    // *************** 生成 select 语句 ************

    String forSelect(Class<?> entity, String selectFields);

    String forSelectFirst(Class<?> entity);

    String forSelectLast(Class<?> entity);

    String forSelectByPrimaryKey(Class<?> entity);

    String forSelectCount(Class<?> entity, String conditions);

    String forSelectWhere(Class<?> entity, String conditions);

    String forSelectGroupBy(Class<?> entity, String groupBy);

    String forSelectOrderBy(Class<?> entity, String orderBy);

    // ***************** 生成 select 字句 ******************

    String generateSelect(Class<?> entity, String selectFields);

    String generateWhere(Class<?> entity, String conditions);

    String generateGroupBy(Class<?> entity, String groupBy);

    String generateHaving(Class<?> entity, String having);

    String generateOrderBy(Class<?> entity, String orderBy);

    String generateSelectFirst(Class<?> entity, boolean needOrder);

    String generateSelectLast(Class<?> entity, boolean needOrder);

    // ***************** 生成 insert 字句 ******************

    String forInsert(Class<?> entity, boolean skipPrimary);


}
