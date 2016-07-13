package com.rayn.jflask.framework.mvc;

import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.core.exception.MultipartException;
import com.rayn.jflask.framework.mvc.model.MultipartFile;
import com.rayn.jflask.framework.mvc.model.Params;
import com.rayn.jflask.framework.util.CollectionUtil;
import com.rayn.jflask.framework.util.FileUtil;
import com.rayn.jflask.framework.util.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * MultipartHelper
 * Created by Raynxxx on 2016/07/12.
 */
public class MultipartHelper {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(MultipartHelper.class);

    // ServletFileUpload static 对象
    private static ServletFileUpload upload;

    public static void init(ServletContext servletContext) {
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

        DiskFileItemFactory factory = new DiskFileItemFactory(
                DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,
                repository
        );

        upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(Constants.UPLOAD_MAX);
    }

    public static boolean isMultipart(HttpServletRequest request) {
        String requestMethod = request.getMethod();
        String contentType = request.getContentType();
        return requestMethod != null && !requestMethod.equalsIgnoreCase("get") &&
                contentType != null && contentType.toLowerCase(Locale.ENGLISH).startsWith("multipart/");
    }

    public static List<Object> parseMultipartParamList(HttpServletRequest request) throws Exception {
        List<Object> paramList = new ArrayList<>();
        List<FileItem> fileItemList;
        try {
            fileItemList = upload.parseRequest(request);
        } catch (FileUploadException e) {
            throw new MultipartException(e);
        }
        Map<String, Object> fieldMap = new HashMap<>();
        List<MultipartFile> multipartFiles = new ArrayList<>();
        for (FileItem fileItem : fileItemList) {
            String fieldName = fileItem.getFieldName();
            logger.debug("[JFlask][MultipartHelper] 表单字段 {}", fieldName);
            if (fileItem.isFormField()) {
                String fieldValue = fileItem.getString(Constants.UTF8);
                fieldMap.put(fieldName, fieldValue);
            } else {
                String fileName = FileUtil.getRealFileName(fileItem.getName());
                if (StringUtil.isEmpty(fileName)) {
                    logger.warn("[JFlask][MultipartHelper] upload file 字段 => {} 文件名为空", fieldName);
                }
                long fileSize = fileItem.getSize();
                String contentType = fileItem.getContentType();
                InputStream inputStream = fileItem.getInputStream();
                MultipartFile multipartFile = new MultipartFile(fieldName, fileName, fileSize,
                        contentType, inputStream);
                multipartFiles.add(multipartFile);
            }
        }
        logger.debug("[JFlask][MultipartHelper] fieldMap {} / files {}", fieldMap.size(), multipartFiles.size());
        if (CollectionUtil.isNotEmpty(fieldMap) || CollectionUtil.isNotEmpty(multipartFiles)) {
            paramList.add(new Params(fieldMap, multipartFiles));
        }
        return paramList;
    }

    public static void uploadFile(String path, MultipartFile file) {
        // TODO
    }

}
