package org.rayn.jflask.framework.mvc.impl;

import org.rayn.jflask.framework.Constants;
import org.rayn.jflask.framework.mvc.ViewResolver;
import org.rayn.jflask.framework.mvc.model.JSPView;
import org.rayn.jflask.framework.mvc.model.Result;
import org.rayn.jflask.framework.util.CollectionUtil;
import org.rayn.jflask.framework.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * DefaultViewResolver
 * Created by Raynxxx on 2016/05/25.
 */
public class DefaultViewResolver implements ViewResolver {
    @Override
    public void resolveView(HttpServletRequest request, HttpServletResponse response, Object routeResult) {
        if (routeResult == null) return;
        if (routeResult instanceof Result) {
            // 结果转为 Json
            Result result = (Result) routeResult;
            WebUtil.responseJSON(response, routeResult);
        } else if (routeResult instanceof JSPView) {
            JSPView jspView = (JSPView) routeResult;
            // 处理 JSP 转发 or 重定向
            if (jspView.isRedirect()) {
                String path = jspView.getPath();
                WebUtil.redirectRequest(path, request, response);
            } else {
                String path = Constants.JSP_PATH + jspView.getPath();
                Map<String, Object> data = jspView.getData();
                if (CollectionUtil.isNotEmpty(data)) {
                    for (Map.Entry<String, Object> entry : data.entrySet()) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    }
                }
                WebUtil.forwardRequest(path, request, response);
            }
        }
    }
}
