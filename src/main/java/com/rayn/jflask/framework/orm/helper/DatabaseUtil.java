package com.rayn.jflask.framework.orm.helper;

import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.orm.DataSourceProvider;
import com.rayn.jflask.framework.orm.TableMapping;
import com.rayn.jflask.framework.orm.model.ColumnInfo;
import com.rayn.jflask.framework.util.CollectionUtil;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * DatabaseUtil
 * Created by Raynxxx on 2016/07/29.
 */
public class DatabaseUtil {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);

    // dataSourceProvider
    private static final DataSourceProvider dataSourceProvider
            = InstanceFactory.getDataSourceProvider();

    // threadConnection
    private static ThreadLocal<Connection> threadConnection = new ThreadLocal<>();

    // QueryRunner
    private static final QueryRunner queryRunner;

    // tableMapping
    private static final TableMapping tableMapping = TableMapping.me();

    static {
        queryRunner = new QueryRunner(getDataSource());
    }

    /**
     * 获取 DataSource
     */
    public static DataSource getDataSource() {
        return dataSourceProvider.getDataSource();
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        Connection conn;
        try {
            conn = threadConnection.get();
            if (conn == null) {
                conn = getDataSource().getConnection();
                if (conn != null) {
                    threadConnection.set(conn);
                }
            }
        } catch (Exception e) {
            logger.error("[JFlask] 获取数据库连接失败");
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static void outputSQL(String sql) {
        if (Constants.JDBC.SHOW_SQL) {
            logger.debug("[JFlask][SQL] ===> {}", sql);
        }
    }

    public <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T result = null;
        try {
            Map<String, String> columnMap = tableMapping.getColumnMap(entityClass);
            if (CollectionUtil.isNotEmpty(columnMap)) {
                result = queryRunner.query(sql, new BeanHandler<T>(entityClass,
                        new BasicRowProcessor(new BeanProcessor(columnMap))), params);
            }
        } catch (SQLException e) {
            logger.error("[JFlask] queryEntity Error", e);
            throw new RuntimeException(e);
        }
        outputSQL(sql);
        return result;
    }

    public <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> result = null;
        try {
            Map<String, String> columnMap = tableMapping.getColumnMap(entityClass);
            if (CollectionUtil.isNotEmpty(columnMap)) {
                result = queryRunner.query(sql, new BeanListHandler<T>(entityClass,
                        new BasicRowProcessor(new BeanProcessor(columnMap))), params);
            }
        } catch (SQLException e) {
            logger.error("[JFlask] queryEntityList Error", e);
            throw new RuntimeException(e);
        }
        outputSQL(sql);
        return result;
    }
}
