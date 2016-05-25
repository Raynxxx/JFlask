package org.rayn.jflask.framework.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HandlerInvoker
 * Created by Raynxxx on 2016/05/25.
 */
public interface HandlerInvoker {

    /**
     * 执行 handler
     *
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理机
     * @throws Exception 异常
     */
    void invokeHandler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception;
}
