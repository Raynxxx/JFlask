package org.rayn.jflask.framework.mvc.impl;

import org.rayn.jflask.framework.InstanceFactory;
import org.rayn.jflask.framework.ioc.BeanFactory;
import org.rayn.jflask.framework.mvc.model.Handler;
import org.rayn.jflask.framework.mvc.HandlerInvoker;
import org.rayn.jflask.framework.mvc.ViewResolver;
import org.rayn.jflask.framework.util.ClassUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * HandlerInvoker
 * Created by Raynxxx on 2016/05/25.
 */
public class DefaultHandlerInvoker implements HandlerInvoker {

    // 视图处理器
    private static final ViewResolver viewResolver = InstanceFactory.getViewResolver();

    // bean 工厂
    private static final BeanFactory beanFactory = InstanceFactory.getBeanFactory();

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

        // 从 bean 工厂获取实例
        Object controllerInstance = beanFactory.getBean(controllerClass);

        // 获取路由方法的参数列表
        List<Object> routeParamList = getRouteParamList(request, handler);

    }

    private List<Object> getRouteParamList(HttpServletRequest request, Handler handler) {
        List<Object> paramList = new ArrayList<>();
        // 获取参数类型
        Class<?>[] paramTypes = handler.getRouteMethod().getParameterTypes();
        // 获取占位符的参数列表
        paramList.addAll(getRoutePathParamList(handler.getPathMatcher(), paramTypes));

        // 取出 request 请求 Map (QueryString 或 FormData)
        // TODO (取得 requestMap)

        return paramList;
    }

    private List<Object> getRoutePathParamList(Matcher routePathMatcher, Class<?>[] routeParamTypes) {
        List<Object> pathParamList = new ArrayList<>();
        for (int i = 1; i <= routePathMatcher.groupCount(); ++i) {
            // 获取占位符上的参数名
            String param = routePathMatcher.group(i);
            // 参数类型识别
            Class<?> paramType = routeParamTypes[i - 1];
            // 支持四种基本类型 int, long, double, String
            if (ClassUtil.isInteger(paramType)) {
                pathParamList.add(ClassUtil.toInteger(param));
            } else if (ClassUtil.isLong(paramType)) {
                pathParamList.add(ClassUtil.toLong(param));
            } else if (ClassUtil.isDouble(paramType)) {
                pathParamList.add(ClassUtil.toDouble(param));
            } else if (ClassUtil.isString(paramType)) {
                pathParamList.add(param);
            }
        }
        return pathParamList;
    }

}
