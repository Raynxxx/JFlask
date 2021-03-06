package com.rayn.jflask.framework.mvc.handler;

import com.rayn.jflask.framework.routing.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HandlerInvoker
 * Created by Raynxxx on 2016/05/25.
 */
public interface HandlerInvoker {

    /**
     * 执行 Handler
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理机
     * @throws Throwable 异常
     */
    Object invokeHandler(HttpServletRequest request, HttpServletResponse response,
                         Handler handler) throws Throwable;
}
