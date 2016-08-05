package com.rayn.jflask.framework.routing;

import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.annotation.web.Controller;
import com.rayn.jflask.framework.annotation.web.Route;
import com.rayn.jflask.framework.core.ClassHelper;
import com.rayn.jflask.framework.core.ClassScanner;
import com.rayn.jflask.framework.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 所有路由函数的扫描初始化
 * RouteInitializer
 * Created by Raynxxx on 2016/05/13.
 */
public class RouteInitializer {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(RouteInitializer.class);

    // 静态初始化
    static {
        logger.info("[JFlask][RouteInitializer] 启动");
        // 取得所有控制器
        List<Class<?>> controllerList = ClassHelper.getClassListByAnnotation(Controller.class);
        if (CollectionUtil.isNotEmpty(controllerList)) {
            // 遍历每一个控制器
            for (Class<?> controller : controllerList) {
                // 取得控制器的命名空间
                Controller controllerAnnotation = controller.getAnnotation(Controller.class);
                String namespace = controllerAnnotation.namespace();
                // 取得控制器中的路由方法
                Method[] routeMethods = controller.getDeclaredMethods();
                if (CollectionUtil.isEmpty(routeMethods))
                    continue;
                for (Method routeMethod : routeMethods) {
                    Route[] routes = routeMethod.getDeclaredAnnotationsByType(Route.class);
                    for (Route route : routes) {
                        String[] methods = route.method();
                        for (String method : methods) {
                            // 转到路由处理方法, 并保存
                            String requestPath = namespace + route.value();
                            Router.addRoute(method, requestPath, controller, routeMethod);
                        }
                    }
                }
            }
        }

    }
}
