package com.rayn.jflask.framework.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * FileUtil
 * Created by Raynxxx on 2016/07/12.
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static String getRealFileName(String fileName) {
        return FilenameUtils.getName(fileName);
    }

    public static File createFile(String filePath) {
        File file;
        try {
            file = new File(filePath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                FileUtils.forceMkdir(parentDir);
            }
        } catch (Exception e) {
            logger.error("[JFlask] 文件创建失败: {}", filePath);
            throw new RuntimeException(e);
        }
        return file;
    }
}
