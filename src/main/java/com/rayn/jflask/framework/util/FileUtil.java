package com.rayn.jflask.framework.util;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FileUtil
 * Created by Raynxxx on 2016/07/12.
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static String getRealFileName(String fileName) {
        return FilenameUtils.getName(fileName);
    }
}
