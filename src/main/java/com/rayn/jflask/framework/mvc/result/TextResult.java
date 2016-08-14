package com.rayn.jflask.framework.mvc.result;

import com.rayn.jflask.framework.mvc.helper.ServletHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TextResult
 * Created by Raynxxx on 2016/07/18.
 */
public class TextResult extends Result {
    private String text;

    public TextResult(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return super.toString() + "<PlainText>";
    }

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response)
            throws Throwable {
        ServletHelper.responseText(response, this.getText());
    }
}
