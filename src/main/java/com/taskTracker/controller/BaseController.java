package com.taskTracker.controller;

import com.taskTracker.Response;
import com.taskTracker.ResponseBuilder;
import com.taskTracker.exception.LogicFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Supplier;

@Slf4j
public abstract class BaseController {

    @Autowired
    protected ResponseBuilder responseBuilder;

    protected <T> Response handleResponse(String message, Supplier<T> callback) {
        try {
            return responseBuilder.createResponse(callback.get(), message, true);
        } catch (LogicFailException exception) {
            log.error(exception.getMessage(), exception);

            return responseBuilder.createResponse(exception.getMessage(), false);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);

            return responseBuilder.createResponse("internal server error", false);
        }
    }
}
