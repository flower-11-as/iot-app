package com.scrawl.iot.paper.exception;

/**
 * Description:
 * Created by as on 2018/4/19.
 */
public class IotHttpException extends RuntimeException {
    public IotHttpException(String message) {
        super(message);
    }

    public IotHttpException(Throwable cause) {
        super(cause);
    }

    public IotHttpException(String message, Throwable cause) {
        super(message, cause);
    }
}
