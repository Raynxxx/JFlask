package com.rayn.jflask.framework.orm;

import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.core.ClassScanner;
import com.rayn.jflask.framework.core.ConfigHelper;
import com.rayn.jflask.framework.orm.model.BaseModel;
import com.rayn.jflask.framework.orm.model.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ORMLoader
 * Created by Raynxxx on 2016/07/15.
 */
public class ORMLoader {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(ORMLoader.class);

    static {
        logger.info("[JFlask] ORMLoader 启动");
        List<Class<?>> modelList = getModelList();
        TableBuilder.build(modelList);
    }
    
    private static List<Class<?>> getModelList() {
        final ClassScanner classScanner = InstanceFactory.getClassScanner();
        final String basePackage = ConfigHelper.getString("app.base_package");
        return classScanner.getClassListBySuper(basePackage, BaseModel.class);
    }
}
