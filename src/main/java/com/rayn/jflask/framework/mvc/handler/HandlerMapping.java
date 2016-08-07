package com.rayn.jflask.framework.mvc.handler;

import com.rayn.jflask.framework.routing.handler.Handler;

/**
 * 根据 StaticHandler 映射查找
 * HandlerMapping
 * Created by Raynxxx on 2016/05/25.
 */
public interface HandlerMapping {


    /**
     * 查找 StaticHandler
     *
     * @param currentRequestMethod 当前请求的方法
     * @param currentRequestPath   当前请求的路径
     * @return StaticHandler
     */
    Handler getHandler(String currentRequestMethod, String currentRequestPath);
}
