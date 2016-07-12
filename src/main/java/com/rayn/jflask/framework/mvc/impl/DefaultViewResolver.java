package com.rayn.jflask.framework.mvc.impl;

import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.mvc.ViewResolver;
import com.rayn.jflask.framework.mvc.model.JSONResult;
import com.rayn.jflask.framework.mvc.model.JSPView;
import com.rayn.jflask.framework.util.CollectionUtil;
import com.rayn.jflask.framework.mvc.ServletHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * DefaultViewResolver
 * Created by Raynxxx on 2016/05/25.
 */
public class DefaultViewResolver implements ViewResolver {

    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);

    @Override
    public void resolveView(HttpServletRequest request, HttpServletResponse response, Object routeResult) {
        if (routeResult != null) {
            if (routeResult instanceof JSPView) {
                // 处理 JSP 转发 or 重定向
                JSPView jspView = (JSPView) routeResult;
                if (jspView.isRedirect()) {
                    String path = jspView.getPath();
                    ServletHelper.redirectRequest(path, request, response);
                } else {
                    String path = Constants.JSP_PATH + jspView.getPath();
                    Map<String, Object> data = jspView.getData();
                    if (CollectionUtil.isNotEmpty(data)) {
                        for (Map.Entry<String, Object> entry : data.entrySet()) {
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }
                    }
                    ServletHelper.forwardRequest(path, request, response);
                }
            } else if (routeResult instanceof JSONResult) {
                // 结果转为 Json
                JSONResult result = (JSONResult) routeResult;
                ServletHelper.responseJSON(response, result);
            } else {
                logger.error("[JFlask][ViewResolver] 不支持的路由返回值类型 {}", routeResult);
            }
        } else {
            logger.error("[JFlask][ViewResolver] 路由返回值不存在");
        }
    }

}
