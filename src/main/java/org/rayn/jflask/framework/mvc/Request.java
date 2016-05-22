package org.rayn.jflask.framework.mvc;

/**
 * Request
 * 封装 Request 信息
 * Created by Raynxxx on 2016/05/14.
 */
public class Request {
    private String method;
    private String path;

    public Request(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
