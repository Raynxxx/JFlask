package org.rayn.jflask.framework.mvc;

import org.rayn.jflask.framework.Constants;
import org.rayn.jflask.framework.InstanceFactory;
import org.rayn.jflask.framework.util.WebUtil;
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
    private static final HandlerExceptionResolver handlerExceptionResolver = InstanceFactory.getHandlerExceptionResolver();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response);
        request.setCharacterEncoding(Constants.UTF8);

        String currentRequestMethod = request.getMethod();
        String currentRequestPath = WebUtil.getRequestPath(request);
        logger.debug("[JFlask] {}:{}", currentRequestMethod, currentRequestPath);

        if (currentRequestPath.endsWith("/")) {
            currentRequestPath = currentRequestPath.substring(0, currentRequestPath.length() - 1);
        }

        Handler handler = handlerMapping.getHandler(currentRequestMethod, currentRequestPath);
        if (handler == null) {
            // TODO(404)
            return;
        }
        try {
            handlerInvoker.invokeHandler(request, response, handler);
        } catch (Exception e) {
            handlerExceptionResolver.resolveHandlerException(request, response, e);
            e.printStackTrace();
        }

    }
}
