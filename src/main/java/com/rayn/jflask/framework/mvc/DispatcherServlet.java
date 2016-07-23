package com.rayn.jflask.framework.mvc;

import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.routing.handler.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

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

    private static final ResultResolver resultResolver = InstanceFactory.getViewResolver();

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
        logger.info("[JFlask][DispatcherServlet] {}:{} {}", currentRequestMethod,
                currentRequestPath, new Date());

        if (currentRequestPath.startsWith(Constants.STATIC_PATH)) {
            handleStaticResource(request, response);
        }

        if (currentRequestPath.endsWith("/") && !currentRequestPath.equals("/")) {
            currentRequestPath = currentRequestPath.substring(0, currentRequestPath.length() - 1);
        }

        Handler handler = handlerMapping.getHandler(currentRequestMethod, currentRequestPath);
        if (handler == null) {
            ServletHelper.responseError(response, HttpServletResponse.SC_NOT_FOUND, "Not Found");
            return;
        }
        WebContext.init(request, response);
        try {
            Object result = handlerInvoker.invokeHandler(request, response, handler);
            resultResolver.resolveResult(request, response, result);
        } catch (Exception e) {
            handlerExceptionResolver.resolveHandlerException(request, response, e);
            logger.error("[JFlask] {}", e.getMessage());
        } finally {
            WebContext.destroy();
            logger.info("[JFlask][DispatcherServlet] ******************************");
        }
    }

    public static void handleStaticResource(HttpServletRequest request,
                                            HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getServletContext().getNamedDispatcher("default");
        if (rd == null) {
            throw new IllegalStateException("A RequestDispatcher could not be located for the default servlet 'default'");
        }
        rd.forward(request, response);
    }
}
