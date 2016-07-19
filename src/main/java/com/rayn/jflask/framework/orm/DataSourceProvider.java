package com.rayn.jflask.framework.orm;

import javax.sql.DataSource;

/**
 * DataSourceProvider
 * Created by Raynxxx on 2016/07/19.
 */
public interface DataSourceProvider {

    DataSource getDataSource();
}
