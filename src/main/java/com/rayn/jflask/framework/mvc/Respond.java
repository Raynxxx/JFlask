package com.rayn.jflask.framework.mvc;

import com.rayn.jflask.framework.mvc.model.JSONResult;
import com.rayn.jflask.framework.mvc.model.JSPView;
import com.rayn.jflask.framework.mvc.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Respond
 * Created by Raynxxx on 2016/07/14.
 */
public class Respond {

    private static Logger logger = LoggerFactory.getLogger(Respond.class);

    /**
     * Respond JSP View
     */
    public static Result jsp(String path,  Object... args) {
        logger.info("[JFlask] Respond => {}", path);

        JSPView view = new JSPView(path);
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
                view.put((String) key, value);
            }
        }
        return view;
    }

    /**
     * Respond JSON Data
     */
    public static Result json(Object data) {
        logger.info("[JFlask] Respond => json {}", data);
        return new JSONResult(data);
    }
}
