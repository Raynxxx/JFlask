package com.rayn.jflask.framework.mvc.impl;

import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.mvc.MultipartHelper;
import com.rayn.jflask.framework.mvc.model.Params;
import com.rayn.jflask.framework.util.ClassUtil;
import com.rayn.jflask.framework.ioc.BeanFactory;
import com.rayn.jflask.framework.mvc.model.Handler;
import com.rayn.jflask.framework.mvc.HandlerInvoker;
import com.rayn.jflask.framework.util.CollectionUtil;
import com.rayn.jflask.framework.mvc.ServletHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * HandlerInvoker
 * Created by Raynxxx on 2016/05/25.
 */
public class DefaultHandlerInvoker implements HandlerInvoker {
    // bean 工厂
    private static final BeanFactory beanFactory = InstanceFactory.getBeanFactory();

    /**
     * 执行 handler
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理机
     * @throws Exception 异常
     */
    @Override
    public Object invokeHandler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception {
        // 获取控制器类和路由方法
        Class<?> controllerClass = handler.getControllerClass();
        Method routeMethod = handler.getRouteMethod();

        // 从 bean 工厂获取实例
        Object controllerInstance = beanFactory.getBean(controllerClass);

        // 获取路由方法的参数列表
        List<Object> routeParamList = getRouteParamList(request, handler);

        if (routeParamList.size() != routeMethod.getParameterTypes().length) {
            throw new RuntimeException(String.format("[%s#%s] 请求参数(%d) 和路由函数参数(%d) 数目不匹配",
                    controllerClass.getName(), routeMethod.getName(), routeParamList.size(),
                    routeMethod.getParameterTypes().length));
        }

        return invokeRouteMethod(controllerInstance, routeMethod, routeParamList);
    }

    private List<Object> getRouteParamList(HttpServletRequest request, Handler handler) throws Exception {
        List<Object> paramList = new ArrayList<>();
        // 获取参数类型
        Class<?>[] paramTypes = handler.getRouteMethod().getParameterTypes();

        // 获取路径占位符的参数列表
        paramList.addAll(getPathParamList(handler.getPathMatcher(), paramTypes));

        // 根据 contentType 类型提供不同处理
        if (MultipartHelper.isMultipart(request)) {
            // Multipart
            paramList.addAll(MultipartHelper.parseMultipartParamList(request));
        } else {
            // 取出 request 请求 Map (QueryString 或 FormData)
            Map<String, Object> requestParamMap = ServletHelper.getRequestParamMap(request);
            if (CollectionUtil.isNotEmpty(requestParamMap)) {
                paramList.add(new Params(requestParamMap));
            }
        }
        return paramList;
    }

    private List<Object> getPathParamList(Matcher routePathMatcher, Class<?>[] routeParamTypes) {
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

    private Object invokeRouteMethod(Object controllerInstance, Method routeMethod, List<Object> routeParamList) throws InvocationTargetException, IllegalAccessException {
        routeMethod.setAccessible(true);
        return routeMethod.invoke(controllerInstance, routeParamList.toArray());
    }

}
