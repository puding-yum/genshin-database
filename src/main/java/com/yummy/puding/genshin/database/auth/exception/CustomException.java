package com.yummy.puding.genshin.database.auth.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception{
    private HttpStatus httpStatus;
    private String message;

    public CustomException() {}

    public CustomException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
