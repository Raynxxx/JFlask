package com.rayn.jflask.framework.orm.dialect;

import com.rayn.jflask.framework.orm.helper.SqlHelper;
import com.rayn.jflask.framework.orm.mapping.ColumnInfo;
import com.rayn.jflask.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        sb.append(generateSelectFirst(entity, true));
        return sb.toString();
    }

    @Override
    public String forSelectLast(Class<?> entity) {
        StringBuffer sb = new StringBuffer("SELECT * FROM ");
        sb.append(SqlHelper.getTableName(entity));
        sb.append(generateSelectLast(entity, true));
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
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNotEmpty(conditions)) {
            String cond = SqlHelper.transferCondition(entity, conditions);
            sb.append(" WHERE ").append(cond);
        }
        return sb.toString();
    }

    @Override
    public String generateGroupBy(Class<?> entity, String groupBy) {
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNotEmpty(groupBy)) {
            String fields = SqlHelper.transferGroupBy(entity, groupBy);
            sb.append(" GROUP BY ").append(fields);
        }
        return sb.toString();
    }

    @Override
    public String generateHaving(Class<?> entity, String having) {
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNotEmpty(having)) {
            String cond = SqlHelper.transferHaving(entity, having);
            sb.append(" HAVING ").append(cond);
        }
        return sb.toString();
    }

    @Override
    public String generateOrderBy(Class<?> entity, String orderBy) {
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNotEmpty(orderBy)) {
            String fields = SqlHelper.transferOrderBy(entity, orderBy);
            sb.append(" ORDER BY ").append(fields);
        }
        return sb.toString();
    }


    @Override
    public String generateSelectFirst(Class<?> entity, boolean needOrder) {
        StringBuffer sb = new StringBuffer();
        if (needOrder) {
            sb.append(" ORDER BY ");
            sb.append(SqlHelper.getTableInfo(entity).getPrimaryKey());
            sb.append(" ASC");
        }
        sb.append(" LIMIT 1");
        return sb.toString();
    }

    @Override
    public String generateSelectLast(Class<?> entity, boolean needOrder) {
        StringBuffer sb = new StringBuffer();
        if (needOrder) {
            sb.append(" ORDER BY ");
            sb.append(SqlHelper.getTableInfo(entity).getPrimaryKey());
            sb.append(" DESC");
        }
        sb.append(" LIMIT 1");
        return sb.toString();
    }

    @Override
    public String forInsert(Class<?> entity, boolean skipPrimary) {
        StringBuffer sb = new StringBuffer("INSERT INTO ");
        sb.append(SqlHelper.getTableName(entity));

        Map<String, ColumnInfo> columnInfoMap = SqlHelper.getColumnInfoMap(entity);
        List<String> columnNames = new ArrayList<>();
        List<String> questionMarks = new ArrayList<>();
        for (Map.Entry<String, ColumnInfo> entry : columnInfoMap.entrySet()) {
            if (skipPrimary && entry.getValue().isPrimary()) {
                continue;
            }
            columnNames.add(entry.getValue().getName());
            questionMarks.add("?");
        }
        sb.append("(").append(StringUtil.join(columnNames.toArray(), ",")).append(")");
        sb.append(" VALUES ");
        sb.append("(").append(StringUtil.join(questionMarks.toArray(), ",")).append(")");
        return sb.toString();
    }

    @Override
    public String forExists(Class<?> entity, String conditions) {
        StringBuffer sb = new StringBuffer("SELECT 1 AS one FROM ");
        sb.append(SqlHelper.getTableName(entity));
        sb.append(generateWhere(entity, conditions));
        sb.append(generateSelectFirst(entity, false));
        return sb.toString();
    }

}
