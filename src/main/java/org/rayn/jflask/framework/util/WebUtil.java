package org.rayn.jflask.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Web 相关工具
 * WebUtil
 * Created by Raynxxx on 2016/05/22.
 */
public class WebUtil {

    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

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
    public static Map<String, Object> getRequestParamMap() {
        Map<String, Object> requestParamMap = new HashMap<>();
        // TODO (requestParamMap)
        return requestParamMap;
    }

}
