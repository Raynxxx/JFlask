package com.rayn.jflask.framework.mvc.model;

import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.mvc.MultipartHelper;
import com.rayn.jflask.framework.mvc.WebContext;

import java.io.InputStream;

/**
 * MultipartFile
 * Created by Raynxxx on 2016/07/12.
 */
public class MultipartFile {
    private String fieldName;
    private String fileName;
    private long fileSize;
    private String contentType;
    private InputStream fileInputStream;

    public MultipartFile(String fieldName, String fileName, long fileSize,
                         String contentType, InputStream fileInputStream) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.fileInputStream = fileInputStream;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void save() {
        WebContext.uploadFile(this);
    }

    public void save(String subDirectory) {
        WebContext.uploadFile(this, subDirectory);
    }

}
