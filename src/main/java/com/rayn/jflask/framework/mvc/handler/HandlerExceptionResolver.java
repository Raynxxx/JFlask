package com.rayn.jflask.framework.mvc.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * StaticHandler 异常处理器
 * HandlerExceptionResolver
 * Created by Raynxxx on 2016/05/25.
 */
public interface HandlerExceptionResolver {

    /**
     * @param request  请求对象
     * @param response 响应对象
     * @param e        异常对象
     */
    void resolveHandlerException(HttpServletRequest request, HttpServletResponse response, Exception e);
}
