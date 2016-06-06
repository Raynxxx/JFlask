package org.rayn.jflask.framework.orm;

import org.rayn.jflask.framework.InstanceFactory;
import org.rayn.jflask.framework.annotation.Column;
import org.rayn.jflask.framework.annotation.Table;
import org.rayn.jflask.framework.core.ClassScanner;
import org.rayn.jflask.framework.core.ConfigHelper;
import org.rayn.jflask.framework.util.CollectionUtil;
import org.rayn.jflask.framework.util.StringUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ORMBuilder
 * Created by Raynxxx on 2016/06/05.
 */
public class ORMBuilder {

    // EntityClass => TableName
    private static final Map<Class<?>, String> tableMap = new HashMap<>();

    // EntityClass => (FieldName => ColumnName)
    private static final Map<Class<?>, Map<String, String>> columnMap = new HashMap<>();

    // 类扫描器
    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();

    // 应用的基础包名
    private static final String basePackage = ConfigHelper.getString("app.base_package");

    static {
        List<Class<?>> entityClassList = classScanner.getClassListBySuper(basePackage, BaseEntity.class);
        for (Class<?> entityClass : entityClassList) {
            putTableMap(entityClass);
            putColumnMap(entityClass);
        }
    }

    private static void putTableMap(Class<?> entityClass) {
        String tableName;
        if (entityClass.isAnnotationPresent(Table.class)) {
            tableName = entityClass.getAnnotation(Table.class).value();
        } else {
            tableName = StringUtil.camelToUnderline(entityClass.getSimpleName());
        }
        tableMap.put(entityClass, tableName);
    }

    private static void putColumnMap(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        if (CollectionUtil.isEmpty(fields)) {
            return;
        }
        Map<String, String> fieldMap = new HashMap<>();
        for (Field field : fields) {
            String columnName;
            if (field.isAnnotationPresent(Column.class)) {
                columnName = field.getAnnotation(Column.class).value();
            } else {
                columnName = StringUtil.camelToUnderline(field.getName());
            }
        }
        columnMap.put(entityClass, fieldMap);
    }

}
