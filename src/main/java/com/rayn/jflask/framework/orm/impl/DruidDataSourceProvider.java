package com.rayn.jflask.framework.orm.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.util.StringUtil;

import javax.sql.DataSource;

/**
 * DruidDataSourceProvider
 * Created by Raynxxx on 2016/08/03.
 */
public class DruidDataSourceProvider extends AbstractDataSourceProvider {

    private String jdbcUrl;
    private String driver;
    private String username;
    private String password;

    @Override
    public void init() {
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
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
