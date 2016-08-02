package com.rayn.jflask.framework.core.exception;

/**
 * QueryException
 * Created by Raynxxx on 2016/08/02.
 */
public class QueryException extends RuntimeException {

    public QueryException() {
        super();
    }

    public QueryException(String message) {
        super(message);
    }

    public QueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryException(Throwable cause) {
        super(cause);
    }
}
