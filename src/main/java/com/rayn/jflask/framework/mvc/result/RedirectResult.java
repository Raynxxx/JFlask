package com.rayn.jflask.framework.mvc.result;

import com.rayn.jflask.framework.mvc.helper.ServletHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RedirectResult
 * Created by Raynxxx on 2016/07/18.
 */
public class RedirectResult extends Result {

    private String path;

    public RedirectResult(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return super.toString() + "<Redirect>";
    }

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response)
            throws Throwable {
        ServletHelper.redirectRequest(this.getPath(), request, response);
    }
}
