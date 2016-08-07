package com.rayn.jflask.framework.mvc.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 页面
 * ResultResolver
 * Created by Raynxxx on 2016/05/25.
 */
public interface ResultResolver {

    void resolveResult(HttpServletRequest request, HttpServletResponse response,
                     Object routeResult);

}
