package com.example.Account_Service;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;

public class CustomErrorMessageUsers extends ResponseEntityExceptionHandler {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public CustomErrorMessageUsers(
            LocalDateTime timestamp,
            int status,
            String error,
            String message,
            String path) {

        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public CustomErrorMessageUsers(
            LocalDateTime timestamp,
            int status,
            String error,
            String path) {

        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public void putMessageNull(){
        this.message=null;
    }
}

