package com.example.skillfpspring;

import com.example.skillfpspring.controller.UserController;
import com.example.skillfpspring.controller.BalanceResponse;
import com.example.skillfpspring.controller.OperationResponse;
import com.example.skillfpspring.entity.User;
import com.example.skillfpspring.interfaces.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Test
    void testGetBalance_UserExists() {
        int userId = 1;
        double balance = 100.0;
        User user = new User();
        user.setId(userId);
        user.setBalance(balance);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<BalanceResponse> responseEntity = userController.getBalance(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        BalanceResponse balanceResponse = responseEntity.getBody();
        assertEquals(balance, balanceResponse.getValue());
    }

    @Test
    void testGetBalance_UserNotFound() {
        int userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<BalanceResponse> responseEntity = userController.getBalance(userId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        BalanceResponse balanceResponse = responseEntity.getBody();
        assertEquals(-1.0, balanceResponse.getValue());
        assertEquals("Пользователь не найден", balanceResponse.getMessage());
    }

    @Test
    void testPutMoney_Success() {
        int userId = 1;
        double initialBalance = 50.0;
        double amountToAdd = 30.0;
        double expectedBalance = initialBalance + amountToAdd;

        User user = new User();
        user.setId(userId);
        user.setBalance(initialBalance);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<OperationResponse> responseEntity = userController.putMoney(userId, amountToAdd);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        OperationResponse operationResponse = responseEntity.getBody();
        assertEquals(1, operationResponse.getCode());

        assertEquals(expectedBalance, user.getBalance());
    }

    @Test
    void testTakeMoney_InsufficientFunds() {
        int userId = 1;
        double initialBalance = 20.0;
        double amountToTake = 30.0;

        User user = new User();
        user.setId(userId);
        user.setBalance(initialBalance);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<OperationResponse> responseEntity = userController.takeMoney(userId, amountToTake);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        OperationResponse operationResponse = responseEntity.getBody();
        assertEquals(0, operationResponse.getCode());
        assertEquals("Недостаточно средств", operationResponse.getMessage());
    }
}