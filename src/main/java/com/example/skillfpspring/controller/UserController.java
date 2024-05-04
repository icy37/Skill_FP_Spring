package com.example.skillfpspring.controller;

import com.example.skillfpspring.entity.Operation;
import com.example.skillfpspring.entity.Transaction;
import com.example.skillfpspring.entity.User;
import com.example.skillfpspring.interfaces.TransactionRepository;
import com.example.skillfpspring.interfaces.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final TransactionRepository transactionRepository;

    @Autowired
    public UserController(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
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
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
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
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
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

    @Transactional
    @PutMapping("/{senderId}/transfer")
    public ResponseEntity<OperationResponse> transferMoney(@PathVariable int senderId,
                                                           @RequestParam int recipientId,
                                                           @RequestParam double amount) {

        User sender = userRepository.findById(senderId).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        User recipient = userRepository.findById(recipientId).orElseThrow(() -> new EntityNotFoundException("Получатель не найден"));

        if (sender.getBalance() < amount) {
            return ResponseEntity.badRequest().body(new OperationResponse(0, "Недостаточно средств"));
        }

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        // Сохранение транзакции
        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setRecipient(recipient);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setOperationType(Operation.OperationType.TRANSFER.ordinal());
        transaction.setStatus("COMPLETED");
        transactionRepository.save(transaction);

        Operation senderOperation = new Operation(sender, Operation.OperationType.TRANSFER, amount);
        senderOperation.setDescription("Перевод средств получателю " + recipient.getId());
        sender.getOperations().add(senderOperation);

        Operation recipientOperation = new Operation(recipient, Operation.OperationType.TRANSFER, amount);
        recipientOperation.setDescription("Перевод средств от отправителя " + sender.getId());
        recipient.getOperations().add(recipientOperation);

        userRepository.save(sender);
        userRepository.save(recipient);

        return ResponseEntity.ok(new OperationResponse(1, "Перевод совершен"));
    }

    @GetMapping("/{id}/operations")
    public ResponseEntity<Object> getOperationList(
            @PathVariable int id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
            List<Operation> operations = user.getOperations().stream()
                    .filter(op -> (startDate == null || op.getOperationDate().isAfter(startDate.atStartOfDay())) &&
                            (endDate == null || op.getOperationDate().isBefore(endDate.plusDays(1).atStartOfDay())))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(operations);
        } catch (EntityNotFoundException e) {
            Operation errorOperation = new Operation();
            errorOperation.setDescription("Пользователь не найден");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorOperation);
        }
    }
}