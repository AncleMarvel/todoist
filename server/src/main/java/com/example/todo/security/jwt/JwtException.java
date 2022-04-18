package com.example.todo.security.jwt;

public class JwtException extends RuntimeException {

    private final String code;

    public JwtException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
