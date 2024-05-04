package com.example.skillfpspring.controller;

import lombok.Data;

@Data
public class OperationResponse {
    private int code;
    private String message;

    public OperationResponse(int code) {
        this.code = code;
    }

    public OperationResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}