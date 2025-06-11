package com.MicroServices.JobApp.Constant;

import org.springframework.http.HttpStatus;

public enum HttpStatusCodeEnum {
    OK(HttpStatus.OK),
    CREATED(HttpStatus.CREATED),
    NO_CONTENT(HttpStatus.NO_CONTENT),
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
    FORBIDDEN(HttpStatus.FORBIDDEN),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    FOUND(HttpStatus.FOUND),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE);

    private final HttpStatus httpStatus;

    HttpStatusCodeEnum(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getCode() {
        return httpStatus.value();
    }

    public String getMessage() {
        return httpStatus.getReasonPhrase();
    }

    public static HttpStatusCodeEnum fromCode(int code) {
        for (HttpStatusCodeEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + code);
    }

    @Override
    public String toString() {
        return "HttpStatusCodeEnum{" +
                "httpStatus=" + httpStatus +
                '}';
    }
}