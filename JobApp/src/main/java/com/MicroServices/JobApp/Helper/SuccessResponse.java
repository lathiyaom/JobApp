package com.MicroServices.JobApp.Helper;

import com.MicroServices.JobApp.Constant.HttpStatusCodeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {

    private Integer statusCode;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public SuccessResponse(HttpStatusCodeEnum statusCode, String message, T data, LocalDateTime timestamp) {
        this.statusCode = statusCode.getCode();
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
