package com.rayn.jflask.framework;

import com.rayn.jflask.framework.orm.DataSourceProvider;
import com.rayn.jflask.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

/**
 * ApplicationListener
 * Created by Raynxxx on 2016/07/07.
 */
@WebListener
public class ApplicationListener implements ServletContextListener {

    // logger
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
    }

    private void initServletMapping(ServletContext context) {
        registerStaticServlet(context);
        registerJspServlet(context);
    }

    private void registerStaticServlet(ServletContext context) {
        ServletRegistration defaultServlet = context.getServletRegistration("default");
        defaultServlet.addMapping("/favicon.ico");
        defaultServlet.addMapping(Constants.STATIC_PATH + "*");
    }

    private void registerJspServlet(ServletContext context) {
        ServletRegistration jspServlet = context.getServletRegistration("jsp");
        String jspPath = Constants.VIEW_PATH;
        if (StringUtil.isNotEmpty(jspPath)) {
            jspServlet.addMapping(jspPath + "*");
        }
    }
}
