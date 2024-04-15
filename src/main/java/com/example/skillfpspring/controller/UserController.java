package com.example.skillfpspring.controller;

import com.example.skillfpspring.entity.Operation;
import com.example.skillfpspring.entity.User;
import com.example.skillfpspring.interfaces.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable int id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body(new BalanceResponse(-1, "Пользователь не найден"));
        }

        return ResponseEntity.ok(new BalanceResponse(user.getBalance()));
    }

    @Transactional
    @PutMapping("/{id}/putMoney")
    public ResponseEntity<OperationResponse> putMoney(@PathVariable int id, @RequestParam double amount) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setBalance(user.getBalance() + amount);
        Operation operation = new Operation(user, Operation.OperationType.DEPOSIT, amount);
        operation.setDescription("Пополнение счета");
        user.getOperations().add(operation);
        userRepository.save(user);
        return ResponseEntity.ok(new OperationResponse(1, "Успех"));
    }

    @Transactional
    @PutMapping("/{id}/takeMoney")
    public ResponseEntity<OperationResponse> takeMoney(@PathVariable int id, @RequestParam double amount) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (user.getBalance() < amount) {
            return ResponseEntity.badRequest().body(new OperationResponse(0, "Недостаточно средств"));
        }
        user.setBalance(user.getBalance() - amount);
        Operation operation = new Operation(user, Operation.OperationType.WITHDRAWAL, amount);
        operation.setDescription("Снятие со счета");
        user.getOperations().add(operation);
        userRepository.save(user);
        return ResponseEntity.ok(new OperationResponse(1, "Успех"));
    }

    @GetMapping("/{id}/operations")
    public ResponseEntity<Object> getOperationList(
            @PathVariable int id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
            List<Operation> operations = user.getOperations().stream()
                    .filter(op -> (startDate == null || op.getOperationDate().isAfter(startDate.atStartOfDay())) &&
                            (endDate == null || op.getOperationDate().isBefore(endDate.plusDays(1).atStartOfDay())))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(operations);
        } catch (EntityNotFoundException e) {
            Operation errorOperation = new Operation();
            errorOperation.setDescription("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorOperation);
        }
    }

    @Data
    private static class BalanceResponse {
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

    @Data
    private static class OperationResponse {
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
}