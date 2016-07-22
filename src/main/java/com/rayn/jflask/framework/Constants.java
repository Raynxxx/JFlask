package com.rayn.jflask.framework;

import com.rayn.jflask.framework.core.ConfigHelper;

/**
 * 常量工厂
 * Constants
 * Created by Raynxxx on 2016/05/21.
 */
public interface Constants {

    String UTF8 = "UTF-8";

    String CONFIG_PROPS = "jflask.properties";

    String VIEW_PATH = ConfigHelper.getString("app.jsp_path", "/WEB-INF/views/");

    String STATIC_PATH = ConfigHelper.getString("app.static_path", "/public/");

    // default 10 MB
    int UPLOAD_MAX = ConfigHelper.getInteger("app.upload_max", 10 * 1024 * 1024);
}
