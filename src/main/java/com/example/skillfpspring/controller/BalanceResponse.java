package com.example.skillfpspring.controller;

import lombok.Data;

@Data
public class BalanceResponse {
    private double value;
    private String message;

    public BalanceResponse(double value) {
        this.value = value;
    }

    public BalanceResponse(double value, String message) {
        this.value = value;
        this.message = message;
    }
}