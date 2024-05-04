package com.example.skillfpspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int operationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.ORDINAL)
    private OperationType operationType;

    private String description;
    private double amount;

    private LocalDateTime operationDate;

    public Operation() {
    }

    public Operation(User user, OperationType operationType, double amount) {
        this.user = user;
        this.operationType = operationType;
        this.amount = amount;
        this.operationDate = LocalDateTime.now();
    }

    public enum OperationType {
        DEPOSIT(0), WITHDRAWAL(1), TRANSFER(2);

        private final int value;

        OperationType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}