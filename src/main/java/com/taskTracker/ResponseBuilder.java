package com.taskTracker;

import org.springframework.stereotype.Component;

@Component
public class ResponseBuilder {

    public Response createResponse(Object data) {
        Response response = new Response();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public Response createResponse(String message, Boolean success) {
        Response response = new Response();
        response.setSuccess(success);
        response.setMessage(message);
        return response;
    }

    public Response createResponse(Object data, String message, Boolean success) {
        Response response = new Response();
        response.setSuccess(success);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
