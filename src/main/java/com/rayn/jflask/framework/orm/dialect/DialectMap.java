package com.rayn.jflask.framework.orm.dialect;

import java.util.HashMap;
import java.util.Map;

/**
 * DialectMap
 * Created by Raynxxx on 2016/08/01.
 */
public class DialectMap {

    private static final Map<String, Class<?>> map = new HashMap<>();

    static {
        map.put("mysql", MysqlDialect.class);
    }

    public static Class<?> getDialectClass(String key) {
        return map.get(key);
    }
}
