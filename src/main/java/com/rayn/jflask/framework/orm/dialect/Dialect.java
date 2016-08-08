package com.rayn.jflask.framework.orm.dialect;


/**
 * Dialect
 * Created by Raynxxx on 2016/08/01.
 */
public interface Dialect {

    // *************** 生成 select 语句 ************

    String forSelect(Class<?> entity, String selectFields);

    String forSelectFirst(Class<?> entity, long size);

    String forSelectLast(Class<?> entity, long size);

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

    String generateSelectFirst(Class<?> entity, boolean needOrder, long size);

    String generateSelectLast(Class<?> entity, boolean needOrder, long size);

    String generateLimit(long size);

    String generateOffset(long size);

    // ***************** 生成 insert 字句 ******************

    String forInsert(Class<?> entity, boolean skipPrimary);

    // ***************** 生成 exist 字句 *******************

    String forExists(Class<?> entity, String conditions);

    // ***************** 生成 delete 字句 ******************

    String forDelete(Class<?> entity, String conditions);

}
