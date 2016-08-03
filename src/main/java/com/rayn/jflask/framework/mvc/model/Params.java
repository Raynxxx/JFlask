package com.rayn.jflask.framework.mvc.model;

import com.rayn.jflask.framework.util.ClassUtil;
import com.rayn.jflask.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Params
 * Created by Raynxxx on 2016/05/30.
 */
public class Params {

    private static final Logger logger = LoggerFactory.getLogger(Params.class);

    private Map<String, Object> requestMap;

    private List<MultipartFile> files;

    public Params(Map<String, Object> requestMap, List<MultipartFile> files) {
        this.requestMap = requestMap;
        this.files = files;
    }

    public Params(Map<String, Object> paramsMap) {
        this(paramsMap, new ArrayList<>());
    }

    public Params() {
        this(new HashMap<>(), new ArrayList<>());
    }

    public boolean contains(String key) {
        return requestMap.containsKey(key);
    }

    public boolean containsFile(String key) {
        return getFile(key) != null;
    }

    public String get(String key) {
        return ClassUtil.toString(requestMap.get(key));
    }

    public List<String> getList(String key) {
        String[] values = ClassUtil.toString(requestMap.get(key)).split(StringUtil.SEPARATOR);
        return new ArrayList<>(Arrays.asList(values));
    }

    public Object getObject(String key) {
        return requestMap.get(key);
    }

    public boolean getBoolean(String key) {
        return ClassUtil.toBoolean(requestMap.get(key));
    }

    public int getInteger(String key) {
        return ClassUtil.toInteger(requestMap.get(key));
    }

    public double getDouble(String key) {
        return ClassUtil.toDouble(requestMap.get(key));
    }

    public float getFloat(String key) {
        return ClassUtil.toFloat(requestMap.get(key));
    }

    public long getLong(String key) {
        return ClassUtil.toLong(requestMap.get(key));
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public MultipartFile getFile(String key) {
        MultipartFile file = null;
        for (MultipartFile multipartFile : files) {
            if (multipartFile.getFieldName().equals(key)) {
                file = multipartFile;
                break;
            }
        }
        return file;
    }

    public <T> T toModel(Class<T> modelType) {
        T instance = null;
        try {
            instance = modelType.newInstance();
            return this.toModel(modelType, instance);
        } catch (Exception e) {
            logger.error("[JFlask] Params.toModel ERROR: {}", modelType.getName(), e);
        }
        return instance;
    }

    public <T> T toModel(Class<T> modelType, T instance) {
        Field[] fields = modelType.getDeclaredFields();
        try {
            for (Field field : fields) {
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();

                if (!this.contains(fieldName))
                    continue;

                logger.debug("[JFlask] Params.toModel {}.{} <= {}", modelType.getSimpleName(),
                        fieldName, this.getObject(fieldName));

                // 根据字段类型注入值
                field.setAccessible(true);
                if (ClassUtil.isBoolean(fieldType)) {
                    field.setBoolean(instance, this.getBoolean(fieldName));
                } else if (ClassUtil.isInteger(fieldType)) {
                    field.setInt(instance, this.getInteger(fieldName));
                } else if (ClassUtil.isDouble(fieldType)) {
                    field.setDouble(instance, this.getDouble(fieldName));
                } else if (ClassUtil.isFloat(fieldType)) {
                    field.setFloat(instance, this.getFloat(fieldName));
                } else if (ClassUtil.isLong(fieldType)) {
                    field.setLong(instance, this.getLong(fieldName));
                } else {
                    field.set(instance, this.getObject(fieldName));
                }
            }
        } catch (Exception e) {
            logger.error("[JFlask] Params.toModel ERROR: {}", modelType.getName(), e);
        }
        return instance;
    }


}
