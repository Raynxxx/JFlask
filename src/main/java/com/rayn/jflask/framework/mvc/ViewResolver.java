package com.rayn.jflask.framework.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 页面
 * ViewResolver
 * Created by Raynxxx on 2016/05/25.
 */
public interface ViewResolver {

    void resolveView(HttpServletRequest request, HttpServletResponse response,
                     Object routeResult);

}
