package com.rayn.jflask.framework.orm.helper;

import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.orm.DataSourceProvider;
import com.rayn.jflask.framework.orm.mapping.TableMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * DatabaseHelper
 * Created by Raynxxx on 2016/07/29.
 */
public class DatabaseHelper {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

    // dataSourceProvider
    private static final DataSourceProvider dataSourceProvider
            = InstanceFactory.getDataSourceProvider();

    // threadConnection
    private static ThreadLocal<Connection> threadConnection = new ThreadLocal<>();

    // tableMapping
    private static final TableMapping tableMapping = TableMapping.me();

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


}
