package com.rayn.jflask.framework.orm.dialect;

import com.rayn.jflask.framework.orm.TableMapping;
import com.rayn.jflask.framework.orm.model.TableInfo;
import com.rayn.jflask.framework.util.StringUtil;

/**
 * MysqlDialect
 * Created by Raynxxx on 2016/08/01.
 */
public class MysqlDialect implements Dialect {

    @Override
    public String forSelectById(Class<?> entity) {
        String conditions = getTable(entity).getPrimaryKey() + " = ?";
        return forSelect(entity, conditions);
    }

    @Override
    public String forSelect(Class<?> entity, String conditions) {
        StringBuilder sb = new StringBuilder("select * from ");
        sb.append(getTableName(entity));
        sb.append(generateWhere(conditions));
        return sb.toString();
    }

    private <T> TableInfo getTable(Class<T> entity) {
        return TableMapping.me().getTable(entity);
    }

    private <T> String getTableName(Class<T> entity) {
        return TableMapping.me().getTableName(entity);
    }

    private String generateWhere(String conditions) {
        String ret = "";
        if (StringUtil.isNotEmpty(conditions)) {
            ret += " where" + conditions;
        }
        return ret;
    }
}
