package com.rayn.jflask.framework.mvc;

import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.annotation.Controller;
import com.rayn.jflask.framework.annotation.Route;
import com.rayn.jflask.framework.core.ClassScanner;
import com.rayn.jflask.framework.core.ConfigHelper;
import com.rayn.jflask.framework.mvc.model.Handler;
import com.rayn.jflask.framework.mvc.model.Request;
import com.rayn.jflask.framework.util.CollectionUtil;
import com.rayn.jflask.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 所有路由函数的扫描初始化
 * RouteBuilder
 * Created by Raynxxx on 2016/05/13.
 */
public class RouteBuilder {

    private static final Logger logger = LoggerFactory.getLogger(RouteBuilder.class);

    // 类扫描器
    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();

    // 应用的基础包名
    private static final String basePackage = ConfigHelper.getString("app.base_package");

    // 路由 Map
    private static final Map<Request, Handler> routeMap = new LinkedHashMap<Request, Handler>();

    // 静态初始化
    static {
        // 取得所有控制器
        List<Class<?>> controllerList = classScanner.getClassListByAnnotation(basePackage, Controller.class);
        if (CollectionUtil.isNotEmpty(controllerList)) {
            // 遍历每一个控制器
            for (Class<?> controller : controllerList) {
                // 取得控制器中的路由方法
                Method[] routeMethods = controller.getDeclaredMethods();
                if (CollectionUtil.isNotEmpty(routeMethods)) {
                    for (Method route : routeMethods) {
                        // 转到路由处理方法, 并保存
                        processRouteMethod(controller, route);
                    }
                }
            }
        }

    }

    /**
     * 根据路由的 HTTP 请求方法, 处理路由所映射的 URL 路径
     */
    private static void processRouteMethod(Class<?> controller, Method route) {
        String requestMethod = null, requestPath = null;
        if (route.isAnnotationPresent(Route.GET.class)) {
            // GET 路由
            requestMethod = "GET";
            requestPath = route.getAnnotation(Route.GET.class).value();
        } else if (route.isAnnotationPresent(Route.POST.class)) {
            // POST 路由
            requestMethod = "POST";
            requestPath = route.getAnnotation(Route.POST.class).value();
        } else if (route.isAnnotationPresent(Route.PUT.class)) {
            // PUT 路由
            requestMethod = "PUT";
            requestPath = route.getAnnotation(Route.PUT.class).value();
        } else if (route.isAnnotationPresent(Route.DELETE.class)) {
            // DELETE 路由
            requestMethod = "DELETE";
            requestPath = route.getAnnotation(Route.DELETE.class).value();
        }
        if (requestMethod != null) {
            putToRouteMap(requestMethod, requestPath, controller, route);
        }
    }

    /**
     * 处理有正则表达式的路由, 存进路由 Map
     */
    private static void putToRouteMap(String requestMethod, String requestPath, Class<?> controller, Method route) {
        if (requestPath.matches(".+<\\w+>.*")) {
            requestPath = StringUtil.replaceAll(requestPath, "<\\w+>", "(\\\\w+)");
        }
        routeMap.put(new Request(requestMethod, requestPath), new Handler(controller, route));
    }

    public static Map<Request, Handler> getRouteMap() {
        return routeMap;
    }
}
