package com.rayn.jflask.framework.orm.helper;

import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.core.exception.QueryException;
import com.rayn.jflask.framework.orm.DataSourceProvider;
import com.rayn.jflask.framework.orm.mapping.TableMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DatabaseHelper
 * Created by Raynxxx on 2016/07/29.
 */
public class DatabaseHelper {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

    // threadConnection
    private static ThreadLocal<Connection> threadConnection = new ThreadLocal<>();


    /**
     * 获取 DataSource
     */
    public static DataSource getDataSource() {
        return InstanceFactory.getDataSourceProvider().getDataSource();
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
            logger.error("[JFlask] 获取数据库连接失败: ", e);
            throw new QueryException(e);
        }
        return conn;
    }

    /**
     * 开启事务
     */
    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                logger.error("[JFlask] 开启事务错误: ", e);
                throw new QueryException(e);
            } finally {
                threadConnection.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                logger.error("[JFlask] 提交事务错误: ", e);
                throw new QueryException(e);
            } finally {
                threadConnection.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                logger.error("[JFlask] 回滚事务错误: ", e);
                throw new QueryException(e);
            } finally {
                threadConnection.remove();
            }
        }
    }



}
