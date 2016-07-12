package com.rayn.jflask.framework.core.exception;

/**
 * MultipartException
 * Created by Raynxxx on 2016/07/12.
 */
public class MultipartException extends RuntimeException {

    public MultipartException() {
        super();
    }

    public MultipartException(String message) {
        super(message);
    }

    public MultipartException(String message, Throwable cause) {
        super(message, cause);
    }

    public MultipartException(Throwable cause) {
        super(cause);
    }
}
