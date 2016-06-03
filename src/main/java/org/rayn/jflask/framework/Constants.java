package org.rayn.jflask.framework;

import org.rayn.jflask.framework.core.ConfigHelper;

/**
 * 常量工厂
 * Constants
 * Created by Raynxxx on 2016/05/21.
 */
public final class Constants {

    public static String UTF8 = "UTF-8";

    public static String CONFIG_PROPS = "jflask.properties";

    public static String JSP_PATH = ConfigHelper.getString("app.jsp_path", "/WEB_INF/jsp");
}
