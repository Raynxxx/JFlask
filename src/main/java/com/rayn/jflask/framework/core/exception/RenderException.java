package com.rayn.jflask.framework.core.exception;

/**
 * RenderException
 * Created by raynxxx on 16/8/13.
 */
public class RenderException extends RuntimeException {

    public RenderException() {
        super();
    }

    public RenderException(String message) {
        super(message);
    }

    public RenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public RenderException(Throwable cause) {
        super(cause);
    }
}