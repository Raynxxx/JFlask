package com.rayn.jflask.framework.routing;

import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.annotation.Controller;
import com.rayn.jflask.framework.annotation.Route;
import com.rayn.jflask.framework.core.ClassScanner;
import com.rayn.jflask.framework.core.ConfigHelper;
import com.rayn.jflask.framework.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 所有路由函数的扫描初始化
 * RouteLoader
 * Created by Raynxxx on 2016/05/13.
 */
public class RouteLoader {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(RouteLoader.class);

    // 静态初始化
    static {
        logger.info("[JFlask] RouteLoader 启动");
        // 取得所有控制器
        List<Class<?>> controllerList = getControllerList();
        if (CollectionUtil.isNotEmpty(controllerList)) {
            // 遍历每一个控制器
            for (Class<?> controller : controllerList) {
                // 取得控制器中的路由方法
                Method[] routeMethods = controller.getDeclaredMethods();
                if (CollectionUtil.isEmpty(routeMethods)) continue;
                for (Method routeMethod : routeMethods) {
                    Route[] routes = routeMethod.getDeclaredAnnotationsByType(Route.class);
                    for (Route route : routes) {
                        String[] methods = route.method();
                        for (String method : methods) {
                            // 转到路由处理方法, 并保存
                            Router.addRoute(method, route.value(), controller, routeMethod);
                        }
                    }
                }
            }
        }

    }

    private static List<Class<?>> getControllerList() {
        final ClassScanner classScanner = InstanceFactory.getClassScanner();
        final String basePackage = ConfigHelper.getString("app.base_package");
        return classScanner.getClassListByAnnotation(basePackage, Controller.class);
    }
}
