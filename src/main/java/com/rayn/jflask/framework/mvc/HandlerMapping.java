package com.rayn.jflask.framework.mvc;

import com.rayn.jflask.framework.mvc.model.Handler;

/**
 * 根据 Handler 映射查找
 * HandlerMapping
 * Created by Raynxxx on 2016/05/25.
 */
public interface HandlerMapping {


    /**
     * 查找 Handler
     *
     * @param currentRequestMethod  当前请求的方法
     * @param currentRequestPath    当前请求的路径
     * @return Handler
     */
    Handler getHandler(String currentRequestMethod, String currentRequestPath);
}
