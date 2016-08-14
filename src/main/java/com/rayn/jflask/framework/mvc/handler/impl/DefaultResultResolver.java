package com.rayn.jflask.framework.mvc.handler.impl;

import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.core.exception.RenderException;
import com.rayn.jflask.framework.mvc.helper.ServletHelper;
import com.rayn.jflask.framework.mvc.handler.ResultResolver;
import com.rayn.jflask.framework.mvc.result.*;
import com.rayn.jflask.framework.util.CollectionUtil;
import com.rayn.jflask.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;

/**
 * DefaultResultResolver
 * Created by Raynxxx on 2016/05/25.
 */
public class DefaultResultResolver implements ResultResolver {

    private static final Logger logger = LoggerFactory.getLogger(ResultResolver.class);


    /**
     * resolveResult
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param routeResult 路由返回结果
     */
    @Override
    public void resolveResult(HttpServletRequest request, HttpServletResponse response,
                              Object routeResult) throws Throwable {
        if (routeResult != null) {
            if (routeResult instanceof Result) {
                Result result = (Result) routeResult;
                result.render(request, response);
            } else {
                logger.error("[JFlask][ResultResolver] 不支持的路由返回值类型 {}", routeResult);
                throw new RenderException();
            }
        } else {
            logger.error("[JFlask][ResultResolver] 路由返回值不存在");
            throw new RenderException();
        }
    }

}
