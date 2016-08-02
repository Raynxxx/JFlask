package com.rayn.jflask.framework.query;

import com.rayn.jflask.framework.core.exception.QueryException;
import com.rayn.jflask.framework.orm.TableMapping;
import com.rayn.jflask.framework.orm.helper.DatabaseHelper;
import com.rayn.jflask.framework.orm.helper.SqlHelper;
import com.rayn.jflask.framework.util.CollectionUtil;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * QueryProvider
 * Created by Raynxxx on 2016/08/02.
 */
public class QueryProvider {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(QueryProvider.class);

    // QueryRunner
    private static final QueryRunner queryRunner
            = new QueryRunner(DatabaseHelper.getDataSource());

    // tableMapping
    private static final TableMapping tableMapping = TableMapping.me();


    /**
     * queryEntity
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T result = null;
        try {
            Map<String, String> columnMap = tableMapping.getColumnMap(entityClass);
            if (CollectionUtil.isNotEmpty(columnMap)) {
                result = queryRunner.query(sql, new BeanHandler<T>(entityClass,
                        new BasicRowProcessor(new BeanProcessor(columnMap))), params);
            }
        } catch (SQLException e) {
            logger.error("[JFlask] queryEntity Error", e);
            throw new QueryException(e);
        }
        SqlHelper.outputSQL(sql);
        return result;
    }

    /**
     * queryEntityList
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> result = null;
        try {
            Map<String, String> columnMap = tableMapping.getColumnMap(entityClass);
            if (CollectionUtil.isNotEmpty(columnMap)) {
                result = queryRunner.query(sql, new BeanListHandler<T>(entityClass,
                        new BasicRowProcessor(new BeanProcessor(columnMap))), params);
            }
        } catch (SQLException e) {
            logger.error("[JFlask] queryEntityList Error", e);
            throw new QueryException(e);
        }
        SqlHelper.outputSQL(sql);
        return result;
    }

    /**
     * queryCount
     */
    public static long queryCount(String sql, Object... params) {
        long result;
        try {
            result = queryRunner.query(sql, new ScalarHandler<Long>("count(*)"), params);
        } catch (SQLException e) {
            logger.error("[JFlask] queryCount Error", e);
            throw new QueryException(e);
        }
        return result;
    }
}
