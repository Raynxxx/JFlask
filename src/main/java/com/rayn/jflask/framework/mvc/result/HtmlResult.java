package com.rayn.jflask.framework.mvc.result;

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
}
