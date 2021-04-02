package com.taskTracker.exception;

public class LogicFailException extends RuntimeException {

    private static final long serialVersionUID = 750326696906279492L;

    public LogicFailException() {
        super();
    }

    public LogicFailException(String message) {
        super(message);
    }

    public LogicFailException(Throwable cause) {
        super(cause);
    }
}
