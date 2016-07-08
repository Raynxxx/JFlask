package com.rayn.jflask.framework.mvc;

import com.avaje.ebeaninternal.server.core.ServletContextListener;
import com.avaje.ebeaninternal.server.lib.util.Str;
import com.rayn.jflask.framework.AppLoader;
import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import java.util.Map;

/**
 * ApplicationListener
 * Created by Raynxxx on 2016/07/07.
 */
@WebListener
public class ApplicationListener extends ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.info("[JFlask] ApplicationListener 启动");
        // 获取 servletContext
        ServletContext servletContext = event.getServletContext();
        // 初始化相关工具类
        AppLoader.init();
        // 添加 Servlet 映射
        initServletMapping(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }

    private void initServletMapping(ServletContext context) {
        Map<String, ? extends ServletRegistration> registrationMap = context.getServletRegistrations();
        for (Map.Entry<String, ? extends ServletRegistration> registration : registrationMap.entrySet()) {
            logger.debug("[JFlask][ApplicationListener] {} => {}", registration.getKey(),
                    registration.getValue());
        }
        registerStaticServlet(context);
        registerJspServlet(context);
    }

    private void registerStaticServlet(ServletContext context) {
        ServletRegistration defaultServlet = context.getServletRegistration("default");
        defaultServlet.addMapping("/index.html");
        defaultServlet.addMapping("/favicon.ico");
        defaultServlet.addMapping(Constants.STATIC_PATH + "*");
    }

    private void registerJspServlet(ServletContext context) {
        ServletRegistration jspServlet = context.getServletRegistration("jsp");
        jspServlet.addMapping("/index.jsp");
        String jspPath = Constants.JSP_PATH;
        if (StringUtil.isNotEmpty(jspPath)) {
            jspServlet.addMapping(jspPath + "*");
        }
    }
}
