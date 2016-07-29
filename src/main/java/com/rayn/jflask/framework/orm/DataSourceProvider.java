package com.rayn.jflask.framework.orm;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DataSourceProvider
 * Created by Raynxxx on 2016/07/19.
 */
public interface DataSourceProvider {

    void init();

    void destroy();

    String getAppConfig(String key);

    DataSource getDataSource();
}
