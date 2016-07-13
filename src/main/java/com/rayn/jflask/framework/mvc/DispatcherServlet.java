package com.rayn.jflask.framework.mvc;

import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.routing.handler.Handler;
import com.rayn.jflask.framework.routing.handler.StaticHandler;
import com.rayn.jflask.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 前端控制器
 * DispatcherServlet
 * Created by Raynxxx on 2016/05/13.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private static final HandlerMapping handlerMapping = InstanceFactory.getHandlerMapping();

    private static final HandlerInvoker handlerInvoker = InstanceFactory.getHandlerInvoker();

    private static final ViewResolver viewResolver = InstanceFactory.getViewResolver();

    private static final HandlerExceptionResolver handlerExceptionResolver = InstanceFactory.getHandlerExceptionResolver();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        MultipartHelper.init(config.getServletContext());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(Constants.UTF8);

        String currentRequestMethod = request.getMethod();
        String currentRequestPath = ServletHelper.getRequestPath(request);
        logger.debug("[JFlask][DispatcherServlet] {}:{}", currentRequestMethod, currentRequestPath);

        if (currentRequestPath.endsWith("/") && !currentRequestPath.equals("/")) {
            currentRequestPath = currentRequestPath.substring(0, currentRequestPath.length() - 1);
        }

        Handler handler = handlerMapping.getHandler(currentRequestMethod, currentRequestPath);
        if (handler == null) {
            ServletHelper.responseError(response, HttpServletResponse.SC_NOT_FOUND, "Not Found");
            return;
        }
        try {
            Object result = handlerInvoker.invokeHandler(request, response, handler);
            viewResolver.resolveView(request, response, result);
        } catch (Exception e) {
            handlerExceptionResolver.resolveHandlerException(request, response, e);
            logger.error("[JFlask] {}", e.getMessage());
        }
    }
}