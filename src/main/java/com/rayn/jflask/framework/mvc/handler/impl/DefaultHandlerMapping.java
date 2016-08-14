package com.rayn.jflask.framework.mvc.handler.impl;

import com.rayn.jflask.framework.mvc.handler.HandlerMapping;
import com.rayn.jflask.framework.routing.RouterManager;
import com.rayn.jflask.framework.routing.handler.Handler;


/**
 * 默认实现的 HandlerMapping
 * DefaultHandlerMapping
 * Created by Raynxxx on 2016/05/25.
 */
public class DefaultHandlerMapping implements HandlerMapping {

    /**
     * 查找 Handler
     *
     * @param currentRequestMethod 当前请求的方法
     * @param currentRequestPath   当前请求的路径
     * @return Handler
     */
    @Override
    public Handler getHandler(String currentRequestMethod, String currentRequestPath) {
        return RouterManager.matchHandler(currentRequestMethod, currentRequestPath);
    }
}
