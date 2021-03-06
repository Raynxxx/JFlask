package com.rayn.jflask.framework.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rayn.jflask.framework.annotation.entity.SkipJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;

/**
 * JsonUtil
 * Created by Raynxxx on 2016/06/02.
 */
public class JsonUtil {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static final Gson gson = new GsonBuilder()
            .setExclusionStrategies(new DefaultExclusionStrategy())
            .serializeNulls()
            .create();

    public static <T> String toJson(T object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }

    /**
     * DefaultExclusionStrategy
     * targetAnnotation @SkipJson will skip
     */
    private static class DefaultExclusionStrategy implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            boolean hasSkipJson = f.getAnnotation(SkipJson.class) != null;
            return hasSkipJson;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }

    }
}
