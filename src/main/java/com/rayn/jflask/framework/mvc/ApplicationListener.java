package com.rayn.jflask.framework.mvc;

import com.avaje.ebeaninternal.server.core.ServletContextListener;
import com.rayn.jflask.framework.AppLoader;
import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.util.StringUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

/**
 * ApplicationListener
 * Created by Raynxxx on 2016/07/07.
 */
@WebListener
public class ApplicationListener extends ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
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
        registerDefaultServlet(context);
        registerJspServlet(context);
    }

    private void registerDefaultServlet(ServletContext context) {
        ServletRegistration defaultServlet = context.getServletRegistration("default");
        defaultServlet.addMapping("/index.html");
        defaultServlet.addMapping("/favicon.ico");
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
