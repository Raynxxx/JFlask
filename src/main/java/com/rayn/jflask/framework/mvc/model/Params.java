package com.rayn.jflask.framework.mvc.model;

import com.rayn.jflask.framework.util.ClassUtil;
import com.rayn.jflask.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public List<String> getList(String key) {
        String[] values = ClassUtil.toString(requestMap.get(key)).split(StringUtil.SEPARATOR);
        return new ArrayList<>(Arrays.asList(values));
    }

    public String get(String key) {
        return ClassUtil.toString(requestMap.get(key));
    }

    public int getInteger(String key) {
        return ClassUtil.toInteger(requestMap.get(key));
    }

    public double getDouble(String key) {
        return ClassUtil.toDouble(requestMap.get(key));
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
}
