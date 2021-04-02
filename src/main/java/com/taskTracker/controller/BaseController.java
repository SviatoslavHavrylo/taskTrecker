package com.taskTracker.controller;

import com.taskTracker.Response;
import com.taskTracker.ResponseBuilder;
import com.taskTracker.exception.LogicFailException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.function.Supplier;

import static com.taskTracker.service.util.Constants.USER_ID;

@Slf4j
public abstract class BaseController {

    @Autowired
    protected ResponseBuilder responseBuilder;

    protected Long getUsername(@NonNull HttpServletRequest request) {
        Object userId = request.getHeader(USER_ID);

        return Objects.isNull(userId) ? null : (Long) userId;
    }

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

    protected ResponseEntity<Resource> createErrorResponse() {
        return ResponseEntity.notFound().build();
    }
}
