package com.rayn.jflask.framework.orm.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.util.StringUtil;

import javax.sql.DataSource;

/**
 * C3p0DataSourceProvider
 * Created by Raynxxx on 2016/07/24.
 */
public class C3p0DataSourceProvider extends AbstractDataSourceProvider {

    private String jdbcUrl;
    private String driver;
    private String username;
    private String password;
    private int maxPoolSize = 100;
    private int minPoolSize = 10;
    private int initialPoolSize = 10;
    private int maxIdleTime = 20;
    private int acquireIncrement = 2;

    private ComboPooledDataSource dataSource;


    @Override
    public void init() throws Exception {
        super.init();
        // init config
        jdbcUrl = getAppConfig(Constants.JDBC.URL);
        driver = getAppConfig(Constants.JDBC.DRIVER);
        username = getAppConfig(Constants.JDBC.USERNAME);
        password = getAppConfig(Constants.JDBC.PASSWORD);
        if (StringUtil.isEmpty(jdbcUrl) || StringUtil.isEmpty(driver)
                || StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            String message = String.format("[JFlask][DataSourceProvider] %s, %s, %s, %s can not be null!",
                    Constants.JDBC.URL, Constants.JDBC.DRIVER, Constants.JDBC.USERNAME, Constants.JDBC.PASSWORD);
            logger.error(message);
            throw new RuntimeException(message);
        }

        // set dataSource
        dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setDriverClass(driver);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setMaxPoolSize(maxPoolSize);
        dataSource.setMinPoolSize(minPoolSize);
        dataSource.setInitialPoolSize(initialPoolSize);
        dataSource.setMaxIdleTime(maxIdleTime);
        dataSource.setAcquireIncrement(acquireIncrement);
    }

    @Override
    public void destroy() {
        super.destroy();
        dataSource.close();
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}