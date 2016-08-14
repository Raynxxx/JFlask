package com.rayn.jflask.framework.mvc.handler.impl;

import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.ioc.BeanFactory;
import com.rayn.jflask.framework.mvc.handler.HandlerInvoker;
import com.rayn.jflask.framework.mvc.helper.MultipartHelper;
import com.rayn.jflask.framework.mvc.helper.ServletHelper;
import com.rayn.jflask.framework.mvc.model.Params;
import com.rayn.jflask.framework.routing.handler.DynamicHandler;
import com.rayn.jflask.framework.routing.handler.Handler;
import com.rayn.jflask.framework.util.ClassUtil;
import com.rayn.jflask.framework.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    // logger
    private static final Logger logger = LoggerFactory.getLogger(HandlerInvoker.class);

    // bean 工厂
    private static final BeanFactory beanFactory = InstanceFactory.getBeanFactory();

    /**
     * 执行 Handler
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理机
     * @throws Throwable 异常
     */
    @Override
    public Object invokeHandler(HttpServletRequest request, HttpServletResponse response,
                                Handler handler) throws Throwable {
        // 获取控制器类和路由方法
        Class<?> controllerClass = handler.getController();
        Method routeMethod = handler.getRouteMethod();

        // 从 bean 工厂获取实例
        Object controllerInstance = beanFactory.getBean(controllerClass);

        // 获取路由方法的参数列表
        List<Object> routeParamList = getRouteParamList(request, handler);

        // 参数数量错误
        if (routeParamList.size() != routeMethod.getParameterTypes().length) {
            throw new RuntimeException(String.format(
                    "[%s#%s] 请求参数(%d) 和路由函数参数(%d) 数目不匹配",
                    controllerClass.getName(), routeMethod.getName(), routeParamList.size(),
                    routeMethod.getParameterTypes().length
            ));
        }

        routeMethod.setAccessible(true);
        return routeMethod.invoke(controllerInstance, routeParamList.toArray());
    }

    private List<Object> getRouteParamList(HttpServletRequest request, Handler handler)
            throws Exception {
        List<Object> paramList = new ArrayList<>();
        // 获取参数类型
        Class<?>[] paramTypes = handler.getRouteMethod().getParameterTypes();

        if (handler instanceof DynamicHandler) {
            // 获取路径占位符的参数列表
            DynamicHandler dynamicHandler = (DynamicHandler) handler;
            paramList.addAll(parsePathParamList(dynamicHandler, paramTypes));
        }

        // 根据 contentType 类型提供不同处理
        if (MultipartHelper.isMultipart(request)) {
            // Multipart
            logger.debug("[JFlask] isMultipart Request");
            List<Object> multipartParamList = MultipartHelper.parseMultipartParamList(request);
            if (CollectionUtil.isNotEmpty(multipartParamList)) {
                paramList.addAll(multipartParamList);
            }
        } else {
            // 取出 request 请求 Map (QueryString 或 FormData)
            Map<String, Object> requestParamMap = ServletHelper.getRequestParamMap(request);
            if (CollectionUtil.isNotEmpty(requestParamMap)) {
                paramList.add(new Params(requestParamMap));
            }
        }
        return paramList;
    }

    private List<Object> parsePathParamList(DynamicHandler dynamicHandler, Class<?>[] routeParamTypes) {
        List<Object> pathParamList = new ArrayList<>();
        Matcher pathMatcher = dynamicHandler.getPathMatcher();
        if (pathMatcher.groupCount() - 1 > routeParamTypes.length) {
            logger.warn("[JFlask] Route {}#{} 参数过少", dynamicHandler.getController().getName(),
                    dynamicHandler.getRouteMethod().getName());
        }
        for (int i = 1; i <= pathMatcher.groupCount(); ++i) {
            // 获取占位符上的参数名
            String param = pathMatcher.group(i);

            // 参数类型识别
            if (i - 1 == routeParamTypes.length) {
                break;
            }
            Class<?> paramType = routeParamTypes[i - 1];

            // 支持基本类型 boolean, int, long, double, float, String
            if (ClassUtil.isBoolean(paramType)) {
                pathParamList.add(ClassUtil.toBoolean(param));
            } else if (ClassUtil.isInteger(paramType)) {
                pathParamList.add(ClassUtil.toInteger(param));
            } else if (ClassUtil.isLong(paramType)) {
                pathParamList.add(ClassUtil.toLong(param));
            } else if (ClassUtil.isDouble(paramType)) {
                pathParamList.add(ClassUtil.toDouble(param));
            } else if (ClassUtil.isFloat(paramType)) {
                pathParamList.add(ClassUtil.toFloat(param));
            } else if (ClassUtil.isString(paramType)) {
                pathParamList.add(param);
            }
        }
        return pathParamList;
    }

}
