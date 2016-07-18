package com.rayn.jflask.framework.mvc;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * WebContext
 * Created by Raynxxx on 2016/07/18.
 */
public class WebContext {

    // 线程专属
    private static final ThreadLocal<WebContext> threadWebContext = new ThreadLocal<>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * 初始化 WebContext
     */
    public static void init(HttpServletRequest request, HttpServletResponse response) {
        WebContext webContext = new WebContext();
        webContext.request = request;
        webContext.response = response;
        threadWebContext.set(webContext);
    }

    /**
     * 销毁 WebContext
     */
    public static void destory() {
        threadWebContext.remove();
    }

    /**
     * 获取 WebContext
     */
    public static WebContext getInstance() {
        return threadWebContext.get();
    }

    public static HttpServletRequest getRequest() {
        return getInstance().request;
    }

    public static HttpServletResponse getResponse() {
        return getInstance().response;
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }

    public static class Session {

        @SuppressWarnings("unchecked")
        public static <T> T get(String key) {
            return (T) getSession().getAttribute(key);
        }

        public static void set(String key, Object value) {
            getSession().setAttribute(key, value);
        }

        public static void remove(String key) {
            getSession().removeAttribute(key);
        }

        public static Map<String, Object> getAll() {
            Map<String, Object> ret = new HashMap<>();
            Enumeration<String> names = getSession().getAttributeNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                ret.put(name, getSession().getAttribute(name));
            }
            return ret;
        }

        public static void removeAll() {
            getSession().invalidate();
        }
    }

    public static class Cookie {

    }

}
