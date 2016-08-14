package com.rayn.jflask.framework.mvc.result;

import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.core.exception.RenderException;
import com.rayn.jflask.framework.mvc.helper.ServletHelper;
import com.rayn.jflask.framework.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * HtmlResult
 * Created by Raynxxx on 2016/07/18.
 */
public class HtmlResult extends Result {

    private String path;

    public HtmlResult(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return super.toString() + "<HTML>";
    }

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response)
            throws Throwable {
        String path = Constants.VIEW_PATH + this.getPath();
        InputStream input = request.getServletContext().getResourceAsStream(path);
        if (input == null) {
            logger.error("[JFlask] Respond.html => {} not found", this.getPath());
            throw new RenderException();
        } else {
            String text = StringUtil.toString(input);
            ServletHelper.responseText(response, text);
        }
    }
}
