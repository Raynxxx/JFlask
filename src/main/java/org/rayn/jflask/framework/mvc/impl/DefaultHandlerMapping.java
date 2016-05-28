package org.rayn.jflask.framework.mvc.impl;

import org.rayn.jflask.framework.mvc.model.Handler;
import org.rayn.jflask.framework.mvc.HandlerMapping;
import org.rayn.jflask.framework.mvc.model.Request;
import org.rayn.jflask.framework.mvc.RouteBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 默认实现的 HandlerMapping
 * DefaultHandlerMapping
 * Created by Raynxxx on 2016/05/25.
 */
public class DefaultHandlerMapping implements HandlerMapping {

    private static final Map<String, Handler> cache = new ConcurrentHashMap<String, Handler>();

    /**
     * 查找 Handler
     *
     * @param currentRequestMethod 当前请求的方法
     * @param currentRequestPath   当前请求的路径
     * @return Handler
     */
    @Override
    public Handler getHandler(String currentRequestMethod, String currentRequestPath) {
        Handler handler = null;
        String cacheKey = currentRequestMethod + ":" + currentRequestPath;

        // 已存在缓存就取出来返回
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        // 遍历所有 route
        Map<Request, Handler> routeMap = RouteBuilder.getRouteMap();
        for (Map.Entry<Request, Handler> route : routeMap.entrySet()) {
            Request request = route.getKey();
            String requestMethod = request.getMethod();
            String requestPath = request.getPath();
            // 由 method 和 path 的正则来判断是否匹配
            if (requestMethod.equalsIgnoreCase(currentRequestMethod)) {
                Matcher matcher = Pattern.compile(requestPath).matcher(currentRequestPath);
                if (matcher.matches()) {
                    handler = route.getValue();
                    handler.setPathMatcher(matcher);
                    // 匹配成功, 退出循环
                    break;
                }
            }
        }
        if (handler != null) {
            cache.put(cacheKey, handler);
        }
        return handler;
    }
}
