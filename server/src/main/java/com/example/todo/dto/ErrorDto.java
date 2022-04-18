package com.example.todo.dto;

public class ErrorDto {

    private String message;

    public ErrorDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public ErrorDto setMessage(String message) {
        this.message = message;
        return this;
    }
}
