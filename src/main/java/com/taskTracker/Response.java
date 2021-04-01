package com.taskTracker;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Response implements Serializable {

    private static final long serialVersionUID = -2639813264530829197L;

    private Boolean success;

    private String message;

    private Object data;
}
