package org.rayn.jflask.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
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
    public static Map<String, Object> getRequestParamMap(HttpServletRequest request) {
        Map<String, Object> requestParamMap = new HashMap<>();
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
        return requestParamMap;
    }

}
