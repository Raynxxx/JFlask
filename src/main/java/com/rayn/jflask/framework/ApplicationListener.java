package com.rayn.jflask.framework;

import com.rayn.jflask.framework.core.ClassHelper;
import com.rayn.jflask.framework.core.ClassScanner;
import com.rayn.jflask.framework.core.ConfigHelper;
import com.rayn.jflask.framework.util.ClassUtil;
import com.rayn.jflask.framework.util.CollectionUtil;
import com.rayn.jflask.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * ApplicationListener
 * Created by Raynxxx on 2016/07/07.
 */
@WebListener
public class ApplicationListener implements ServletContextListener {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(ApplicationListener.class);

    // classScanner
    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.info("[JFlask][ApplicationListener] 启动");
        // 获取 servletContext
        ServletContext servletContext = event.getServletContext();
        // 添加 Servlet 映射
        registerStaticServlet(servletContext);
        registerJspServlet(servletContext);

        // 初始化相关组件
        ComponentLoader.init();

        // User defined Application init
        Application application = getApplication();
        application.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

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

    private Application getApplication() {
        Application application = null;
        try {
            List<Class<?>> applicationList = ClassHelper.getClassListBySuper(Application.class);
            if (CollectionUtil.isNotEmpty(applicationList)) {
                if (applicationList.size() >= 2) {
                    logger.warn("[JFlask] More than one class extends from <Application>, that's useless.");
                }
                Class<?> applicationClass = applicationList.get(0);
                application = (Application) applicationClass.newInstance();
            }
        } catch (Exception e) {
            logger.error("[JFlask] Load User Defined Application Fail {}", e);
        } finally {
            if (application == null) {
                application = new Application();
            }
        }
        return application;
    }
}
