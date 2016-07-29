package com.rayn.jflask.framework.routing;

import com.rayn.jflask.framework.routing.handler.DynamicHandler;
import com.rayn.jflask.framework.routing.handler.Handler;
import com.rayn.jflask.framework.routing.handler.StaticHandler;
import com.rayn.jflask.framework.routing.request.DynamicRequest;
import com.rayn.jflask.framework.routing.request.Request;
import com.rayn.jflask.framework.routing.request.StaticRequest;
import com.rayn.jflask.framework.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Router
 * Created by Raynxxx on 2016/07/12.
 */
public class Router {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(Router.class);

    // Rule Regex String
    private static final String ruleString =
            "(?<static>[^<]*)                       # static rule data\n" +
            "<\n" +
            "(?<variable>[a-zA-Z_][a-zA-Z0-9_]*)    # variable name\n" +
            ">";

    private static final Pattern urlRulePattern = Pattern.compile(ruleString, Pattern.COMMENTS);


    // 路由 Map
    private static final Map<Request, StaticHandler> staticRouters = new LinkedHashMap<>();
    private static final Map<Request, DynamicHandler> dynamicRouters = new LinkedHashMap<>();


    /**
     * 根据当前请求路径，匹配路由处理器
     */
    public static Handler matchHandler(String curMethod, String curRequestPath) {
        // 查找静态路由
        if (CollectionUtil.isNotEmpty(staticRouters)) {
            for (Map.Entry<Request, StaticHandler> router : staticRouters.entrySet()) {
                StaticRequest request = (StaticRequest) router.getKey();
                String method = request.getMethod();
                String requestPath = request.getPath();
                if (curMethod.equalsIgnoreCase(method) && curRequestPath.equals(requestPath)) {
                    return router.getValue();
                }
            }
        }

        // 查找动态路由
        if (CollectionUtil.isNotEmpty(dynamicRouters)) {
            for (Map.Entry<Request, DynamicHandler> router : dynamicRouters.entrySet()) {
                DynamicRequest request = (DynamicRequest) router.getKey();
                String method = request.getMethod();
                Pattern pathPattern = request.getPathPattern();
                if (curMethod.equalsIgnoreCase(method)) {
                    Matcher matcher = pathPattern.matcher(curRequestPath);
                    if (matcher.matches()) {
                        DynamicHandler handler = router.getValue();
                        handler.setPathMatcher(matcher);
                        // handler.setNamedGroups(StringUtil.getNamedGroups(pathPattern));
                        return handler;
                    }
                }
            }
        }

        return null;
    }

    /**
     * 添加 Route
     */
    public static void addRoute(String requestMethod, String requestPath,
                                Class<?> controller, Method routeMethod) {
        if (!requestPath.contains("<")) {
            Request request = new StaticRequest(requestMethod, requestPath);
            StaticHandler handler = new StaticHandler(controller, routeMethod);
            staticRouters.put(request, handler);
        } else {
            String regexPath = getDynamicRegexPath(requestPath);
            DynamicHandler handler = new DynamicHandler(controller, routeMethod);
            Request request = new DynamicRequest(requestMethod, Pattern.compile(regexPath));
            dynamicRouters.put(request, handler);
        }
        logger.debug("[JFlask][Router] {}:{} => {}#{}", requestMethod, requestPath,
                controller.getName(), routeMethod.getName());
    }

    /**
     * 构造动态 DynamicHandler regexPath
     */
    public static String getDynamicRegexPath(String requestPath) {
        List<Rule> rules = getRuleParts(requestPath);
        StringBuilder sb = new StringBuilder();
        for (Rule rule : rules) {
            sb.append(rule.getStaticPart());
            sb.append(String.format("(?<%s>[^/]+)", rule.getVariable()));
        }
        return sb.toString();
    }


    /**
     * 匹配 path 中的所有动态规则
     */
    private static List<Rule> getRuleParts(String requestPath) {
        Matcher matcher = urlRulePattern.matcher(requestPath);
        List<Rule> rules = new ArrayList<>();
        int start = 0;
        while (matcher.find(start)) {
            start = matcher.end();
            rules.add(new Rule(matcher.group("static"), matcher.group("variable")));
        }
        return rules;
    }

}
