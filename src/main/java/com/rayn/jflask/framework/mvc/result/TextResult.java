package com.rayn.jflask.framework.mvc.result;

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
}
