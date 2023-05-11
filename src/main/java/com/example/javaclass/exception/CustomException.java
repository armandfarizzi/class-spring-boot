package com.example.javaclass.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {
    private int code;
    private String message;
    private String error;

    public CustomException(int code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }

    public CustomException(int code, String error, String message){
        super(message);
        this.code = code;
        this.message = message;
        this.error = error;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
        this.message = message;
        this.error = cause.getMessage();
    }
}
