package com.rayn.jflask.framework.mvc.result;

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
        return "<RedirectResult>";
    }
}
