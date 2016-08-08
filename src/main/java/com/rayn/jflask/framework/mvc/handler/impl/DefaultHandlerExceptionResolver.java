package com.rayn.jflask.framework.mvc.handler.impl;

import com.rayn.jflask.framework.mvc.handler.HandlerExceptionResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DefaultHandlerExceptionResolver
 * Created by Raynxxx on 2016/05/25.
 */
public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(HandlerExceptionResolver.class);


    /**
     * resolveHandlerException
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param ex       异常对象
     */
    @Override
    public void resolveHandlerException(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
        logger.error("[JFlask] {}", ex.getMessage());
        ex.printStackTrace();
    }
}
