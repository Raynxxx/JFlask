package org.rayn.jflask.framework.mvc.impl;

import org.rayn.jflask.framework.mvc.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DefaultHandlerExceptionResolver
 * Created by Raynxxx on 2016/05/25.
 */
public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver {


    /**
     * @param request  请求对象
     * @param response 响应对象
     * @param e        异常对象
     */
    @Override
    public void resolveHandlerException(HttpServletRequest request, HttpServletResponse response, Exception e) {

    }
}
