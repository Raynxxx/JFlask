package com.rayn.jflask.framework.orm.dialect;

import com.rayn.jflask.framework.orm.helper.SqlHelper;
import com.rayn.jflask.framework.util.StringUtil;

/**
 * MysqlDialect
 * Created by Raynxxx on 2016/08/01.
 */
public class MysqlDialect implements Dialect {

    @Override
    public String forSelect(Class<?> entity, String selectFields) {
        StringBuffer sb = new StringBuffer("SELECT ");
        sb.append(generateSelect(entity, selectFields));
        sb.append(" FROM ");
        sb.append(SqlHelper.getTableName(entity));
        return sb.toString();
    }

    @Override
    public String forSelectFirst(Class<?> entity) {
        StringBuffer sb = new StringBuffer("SELECT * FROM ");
        sb.append(SqlHelper.getTableName(entity));
        sb.append(generateSelectFirst(entity));
        return sb.toString();
    }

    @Override
    public String forSelectLast(Class<?> entity) {
        StringBuffer sb = new StringBuffer("SELECT * FROM ");
        sb.append(SqlHelper.getTableName(entity));
        sb.append(generateSelectLast(entity));
        return sb.toString();
    }

    @Override
    public String forSelectByPrimaryKey(Class<?> entity) {
        String conditions = SqlHelper.getTableInfo(entity).getPrimaryKey() + " = ?";
        return forSelectWhere(entity, conditions);
    }

    @Override
    public String forSelectCount(Class<?> entity, String conditions) {
        StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM ");
        sb.append(SqlHelper.getTableName(entity));
        sb.append(generateWhere(entity, conditions));
        return sb.toString();
    }

    @Override
    public String forSelectWhere(Class<?> entity, String conditions) {
        StringBuffer sb = new StringBuffer("SELECT * FROM ");
        sb.append(SqlHelper.getTableName(entity));
        sb.append(generateWhere(entity, conditions));
        return sb.toString();
    }

    @Override
    public String forSelectGroupBy(Class<?> entity, String groupBy) {
        StringBuffer sb = new StringBuffer("SELECT * FROM ");
        sb.append(SqlHelper.getTableName(entity));
        sb.append(generateGroupBy(entity, groupBy));
        return sb.toString();
    }

    @Override
    public String forSelectOrderBy(Class<?> entity, String orderBy) {
        StringBuffer sb = new StringBuffer("SELECT * FROM ");
        sb.append(SqlHelper.getTableName(entity));
        sb.append(generateOrderBy(entity, orderBy));
        return null;
    }

    @Override
    public String generateSelect(Class<?> entity, String selectFields) {
        String ret;
        if (StringUtil.isNotEmpty(selectFields)) {
            ret = SqlHelper.transferSelect(entity, selectFields);
        } else {
            ret = "*";
        }
        return ret;
    }

    @Override
    public String generateWhere(Class<?> entity, String conditions) {
        String ret = "";
        if (StringUtil.isNotEmpty(conditions)) {
            String cond = SqlHelper.transferCondition(entity, conditions);
            ret += " WHERE " + cond;
        }
        return ret;
    }

    @Override
    public String generateGroupBy(Class<?> entity, String groupBy) {
        String ret = "";
        if (StringUtil.isNotEmpty(groupBy)) {
            String fields = SqlHelper.transferGroupBy(entity, groupBy);
            ret += " GROUP BY " + fields;
        }
        return ret;
    }

    @Override
    public String generateHaving(Class<?> entity, String having) {
        String ret = "";
        if (StringUtil.isNotEmpty(having)) {
            String cond = SqlHelper.transferHaving(entity, having);
            ret += " HAVING " + cond;
        }
        return ret;
    }

    @Override
    public String generateOrderBy(Class<?> entity, String orderBy) {
        String ret = "";
        if (StringUtil.isNotEmpty(orderBy)) {
            String fields = SqlHelper.transferOrderBy(entity, orderBy);
            ret += " ORDER BY " + fields;
        }
        return ret;
    }


    @Override
    public String generateSelectFirst(Class<?> entity) {
        StringBuffer sb = new StringBuffer(" ORDER BY ");
        sb.append(SqlHelper.getTableInfo(entity).getPrimaryKey());
        sb.append(" ASC LIMIT 1");
        return sb.toString();
    }

    @Override
    public String generateSelectLast(Class<?> entity) {
        StringBuffer sb = new StringBuffer(" ORDER BY ");
        sb.append(SqlHelper.getTableInfo(entity).getPrimaryKey());
        sb.append(" DESC LIMIT 1");
        return sb.toString();
    }



}
