package com.rayn.jflask.framework.mvc;

import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.util.CollectionUtil;
import com.rayn.jflask.framework.util.JsonUtil;
import com.rayn.jflask.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * ServletHelper
 * ServletHelper
 * Created by Raynxxx on 2016/05/22.
 */
public class ServletHelper {

    private static final Logger logger = LoggerFactory.getLogger(ServletHelper.class);

    /**
     * 获取请求 URL, 带有参数
     */
    public static String getRequestPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String pathInfo = StringUtil.getOrDefault(request.getPathInfo(), "");
        return servletPath + pathInfo;
    }

    /**
     * 获取请求中的所有参数
     */
    public static Map<String, Object> getRequestParamMap(HttpServletRequest request) {
        Map<String, Object> requestParamMap = new HashMap<>();
        try {
            // 取得所有请求参数名
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paraName = parameterNames.nextElement();
                String[] paraValues = request.getParameterValues(paraName);
                if (CollectionUtil.isNotEmpty(paraValues)) {
                    if (paraValues.length == 1) {
                        requestParamMap.put(paraName, paraValues[0]);
                    } else {
                        requestParamMap.put(paraName, StringUtil.join(paraValues));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("[JFlask] 获取请求参数错误", e);
            throw new RuntimeException(e);
        }
        return requestParamMap;
    }

    /**
     * 转发请求到 JSP
     */
    public static void forwardRequest(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.debug("[JFlask] forward to {}", path);
            request.getRequestDispatcher(path).forward(request, response);
        } catch (Exception e) {
            logger.error("转发请求错误", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 重定向请求
     */
    public static void redirectRequest(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.debug("[JFlask] redirect to {}", path);
            response.sendRedirect(request.getContextPath() + path);
        } catch (Exception e) {
            logger.error("重定向错误", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 响应 JSON 数据
     */
    public static void responseJSON(HttpServletResponse response, Object data) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding(Constants.UTF8);
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JsonUtil.toJson(data));
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            logger.error("写入响应数据错误", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 响应错误
     */
    public static void responseError(HttpServletResponse response, int errorCode, String message) {
        try {
            response.sendError(errorCode, message);
        } catch (Exception e) {
            logger.error("响应错误代码错误", e);
            throw new RuntimeException(e);
        }
    }

}
