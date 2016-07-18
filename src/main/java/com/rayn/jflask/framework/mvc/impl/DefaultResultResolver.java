package com.rayn.jflask.framework.mvc.impl;

import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.mvc.ServletHelper;
import com.rayn.jflask.framework.mvc.ResultResolver;
import com.rayn.jflask.framework.mvc.result.*;
import com.rayn.jflask.framework.util.CollectionUtil;
import com.rayn.jflask.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;

/**
 * DefaultResultResolver
 * Created by Raynxxx on 2016/05/25.
 */
public class DefaultResultResolver implements ResultResolver {

    private static final Logger logger = LoggerFactory.getLogger(ResultResolver.class);

    @Override
    public void resolveResult(HttpServletRequest request, HttpServletResponse response, Object routeResult) {
        if (routeResult != null) {
            /**
             * 处理 JSP 转发
             */
            if (routeResult instanceof JspResult) {
                JspResult jspResult = (JspResult) routeResult;
                String path = Constants.VIEW_PATH + jspResult.getPath();
                Map<String, Object> data = jspResult.getData();
                if (CollectionUtil.isNotEmpty(data)) {
                    for (Map.Entry<String, Object> entry : data.entrySet()) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    }
                }
                ServletHelper.forwardRequest(path, request, response);
            }
            /**
             * 重定向
             */
            else if (routeResult instanceof RedirectResult) {
                RedirectResult result = (RedirectResult) routeResult;
                ServletHelper.redirectRequest(result.getPath(), request, response);
            }
            /**
             * 结果响应 HTML 文件
             */
            else  if (routeResult instanceof HtmlResult) {
                HtmlResult result = (HtmlResult) routeResult;
                String path = Constants.VIEW_PATH + result.getPath();
                InputStream input = request.getServletContext().getResourceAsStream(path);
                if (input == null) {
                    logger.error("[JFlask] Respond.html => {} not found", result.getPath());
                } else {
                    String text = StringUtil.toString(input);
                    ServletHelper.responseText(response, text);
                }
            }
            /**
             * 结果响应 纯文本数据
             */
            else if (routeResult instanceof TextResult) {
                // 结果响应 纯文本数据
                TextResult result = (TextResult) routeResult;
                ServletHelper.responseText(response, result.getText());
            }
            /**
             * 结果转为 Json
             */
            else if (routeResult instanceof JsonResult) {
                JsonResult result = (JsonResult) routeResult;
                ServletHelper.responseJSON(response, result.getData());
            }
            /**
             * 未支持方法
             */
            else {
                logger.error("[JFlask][ResultResolver] 不支持的路由返回值类型 {}", routeResult);
            }
        } else {
            logger.error("[JFlask][ResultResolver] 路由返回值不存在");
        }
    }

}
