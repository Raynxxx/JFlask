package com.rayn.jflask.framework.mvc;

import com.rayn.jflask.framework.mvc.result.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Respond
 * Created by Raynxxx on 2016/07/14.
 */
public class Respond {

    private static Logger logger = LoggerFactory.getLogger(Respond.class);

    /**
     * Respond redirect
     */
    public static Result redirect(String path) {
        logger.info("[JFlask] Respond.redirect => {}", path);
        return new RedirectResult(path);
    }

    /**
     * Respond JSP View
     */
    public static Result jsp(String path, Object... args) {
        logger.info("[JFlask] Respond.jsp => {}", path);

        JspResult jspResult = new JspResult(path);
        int length = args.length;
        if (length % 2 != 0) {
            String message = String.format("[JFlask] Respond => %s 参数不合法", path);
            logger.error(message);
            throw new RuntimeException(message);
        }
        for (int i = 0; i < length; i += 2) {
            Object key = args[i];
            Object value = args[i + 1];
            if (!(key instanceof String)) {
                String message = String.format("[JFlask] Respond => %s 参数不合法, " +
                        "%s 必须是 String 类型", path, key);
                logger.error(message);
                throw new RuntimeException(message);
            } else {
                jspResult.put((String) key, value);
            }
        }
        return jspResult;
    }

    /**
     * Respond JSON Data
     */
    public static Result json(Object data) {
        logger.info("[JFlask] Respond.json => {}", data);
        return new JsonResult(data);
    }

    /**
     * Respond HTML File
     */
    public static Result html(String htmlPath) {
        logger.info("[JFlask] Respond.html => {}", htmlPath);
        return new HtmlResult(htmlPath);
    }

    /**
     * Respond Pure Text Data
     */
    public static Result text(String text) {
        logger.info("[JFlask] Respond.text => {}", text);
        return new TextResult(text);
    }
}
