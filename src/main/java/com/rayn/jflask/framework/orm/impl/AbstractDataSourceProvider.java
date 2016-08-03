package com.rayn.jflask.framework.orm.impl;

import com.rayn.jflask.framework.core.ConfigHelper;
import com.rayn.jflask.framework.orm.DataSourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractDataSourceProvider
 * Created by Raynxxx on 2016/07/24.
 */
public abstract class AbstractDataSourceProvider implements DataSourceProvider {

    protected static final Logger logger = LoggerFactory.getLogger(DataSourceProvider.class);


    @Override
    public void init() {
        logger.info("[JFlask][DataSourceProvider] 初始化");
    }

    @Override
    public void destroy() {
        logger.info("[JFlask][DataSourceProvider] 销毁中");
    }

    @Override
    public String getAppConfig(String key) {
        return ConfigHelper.getString(key);
    }
}
