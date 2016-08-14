package com.rayn.jflask.framework.mvc.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 页面
 * ResultResolver
 * Created by Raynxxx on 2016/05/25.
 */
public interface ResultResolver {

    /**
     * resolveResult
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param routeResult 路由返回结果
     */
    void resolveResult(HttpServletRequest request, HttpServletResponse response,
                     Object routeResult) throws Throwable;

}
