package com.MicroServices.JobApp.Helper;

import com.MicroServices.JobApp.Constant.HttpStatusCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private int statusCode;
    private String message;
    private LocalDateTime timestamp;
    private String details;
    private String path;

    public ErrorResponse(HttpStatusCodeEnum statusCode, String message, LocalDateTime timestamp, String details, String path) {
        this.statusCode = statusCode.getCode();
        this.message = message;
        this.timestamp = timestamp;
        this.details = details;
        this.path = path;
    }

    // Getters and Setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}