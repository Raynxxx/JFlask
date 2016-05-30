package org.rayn.jflask.framework.mvc.model;

import java.util.Map;

/**
 * JSPView
 * Created by Raynxxx on 2016/05/30.
 */
public class JSPView {
    private String path;
    private Map<String, Object> data;

    public JSPView(String path) {
        this.path = path;
    }
}
