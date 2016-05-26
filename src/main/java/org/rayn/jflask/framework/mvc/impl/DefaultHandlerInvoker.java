package org.rayn.jflask.framework.mvc.impl;

import org.rayn.jflask.framework.InstanceFactory;
import org.rayn.jflask.framework.mvc.model.Handler;
import org.rayn.jflask.framework.mvc.HandlerInvoker;
import org.rayn.jflask.framework.mvc.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * HandlerInvoker
 * Created by Raynxxx on 2016/05/25.
 */
public class DefaultHandlerInvoker implements HandlerInvoker {

    private static final ViewResolver viewResolver = InstanceFactory.getViewResolver();

    /**
     * 执行 handler
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理机
     * @throws Exception 异常
     */
    @Override
    public void invokeHandler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception {
        // 获取控制器类和路由方法
        Class<?> controllerClass = handler.getControllerClass();
        Method routeMethod = handler.getRouteMethod();

    }
}
