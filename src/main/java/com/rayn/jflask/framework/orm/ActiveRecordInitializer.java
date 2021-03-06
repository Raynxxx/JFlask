package com.rayn.jflask.framework.orm;

import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.core.ClassHelper;
import com.rayn.jflask.framework.core.ConfigHelper;
import com.rayn.jflask.framework.orm.dialect.Dialect;
import com.rayn.jflask.framework.orm.dialect.DialectMap;
import com.rayn.jflask.framework.util.ClassUtil;
import com.rayn.jflask.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ActiveRecordInitializer
 * Created by Raynxxx on 2016/07/15.
 */
public class ActiveRecordInitializer {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(ActiveRecordInitializer.class);

    // dataSourceProvider
    private static final DataSourceProvider dataSourceProvider
            = InstanceFactory.getDataSourceProvider();

    // defaultDialect
    private static final Dialect defaultDialect;

    static {
        logger.info("[JFlask][ActiveRecordInitializer] 启动");

        // 初始化 defaultDialect
        String dialectConfig = ConfigHelper.getString(Constants.JDBC.DIALECT);
        if (StringUtil.isEmpty(dialectConfig)) {
            throw new RuntimeException(String.format("[JFlask] %s must be config!",
                    Constants.JDBC.DIALECT));
        }
        try {
            Class<?> dialectClass = DialectMap.getDialectClass(dialectConfig);
            if (dialectClass != null) {
                defaultDialect = (Dialect) ClassUtil.loadClass(dialectClass.getName())
                        .newInstance();
            } else {
                throw new RuntimeException(String.format("[JFlask] dialect %s does not exists!",
                        dialectConfig));
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("[JFlask] load dialect %s error! %s",
                    dialectConfig, e));
        }

        // 初始化 dataSourceProvider
        try {
            dataSourceProvider.init();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        List<Class<?>> modelList = ClassHelper.getClassListBySuper(BaseModel.class);
        // 任务转交给 TableBuilder
        TableBuilder.build(modelList, defaultDialect);
    }

    public static Dialect getDefaultDialect() {
        return defaultDialect;
    }
}
