package com.scrawl.iot.paper.exception;

/**
 * Desc:
 * Create by scrawl on 2018/4/19
 */
public class PaperHttpException extends RuntimeException {

    public PaperHttpException(String message) {
        super(message);
    }

    public PaperHttpException(Throwable cause) {
        super(cause);
    }

    public PaperHttpException(String message, Throwable cause) {
        super(message, cause);
    }
}
