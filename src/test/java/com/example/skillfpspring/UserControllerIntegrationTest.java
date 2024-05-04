package com.example.skillfpspring;

import com.example.skillfpspring.controller.BalanceResponse;
import com.example.skillfpspring.controller.OperationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void testPutMoney_Success() {
        int userId = 1;
        double amount = 50.0;

        ResponseEntity<OperationResponse> responseEntity = restTemplate.exchange("/api/users/{id}/putMoney?amount={amount}", HttpMethod.PUT, null, OperationResponse.class, userId, amount);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        OperationResponse operationResponse = responseEntity.getBody();
        assertEquals(1, operationResponse.getCode());
    }

    @Test
    void testTakeMoney_InsufficientFunds() {
        int userId = 1;
        double amount = 200.0;

        ResponseEntity<OperationResponse> responseEntity = restTemplate.exchange("/api/users/{id}/takeMoney?amount={amount}", HttpMethod.PUT, null, OperationResponse.class, userId, amount);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        OperationResponse operationResponse = responseEntity.getBody();
        assertEquals(0, operationResponse.getCode());
        assertEquals("Недостаточно средств", operationResponse.getMessage());
    }

    @Test
    void testGetBalance_UserExists() {
        int userId = 1;
        ResponseEntity<BalanceResponse> responseEntity = restTemplate.getForEntity("/api/users/{id}/balance", BalanceResponse.class, userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        BalanceResponse balanceResponse = responseEntity.getBody();
        assertEquals(50.0, balanceResponse.getValue());
    }

    @Test
    void testGetBalance_UserNotFound() {
        int userId = 999;
        ResponseEntity<BalanceResponse> responseEntity = restTemplate.getForEntity("/api/users/{id}/balance", BalanceResponse.class, userId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        BalanceResponse balanceResponse = responseEntity.getBody();
        assertEquals(-1.0, balanceResponse.getValue());
        assertEquals("Пользователь не найден", balanceResponse.getMessage());
    }

    @Test
    void testTakeMoney_Success() {
        int userId = 1;
        double amount = 50.0;

        ResponseEntity<OperationResponse> responseEntity = restTemplate.exchange("/api/users/{id}/takeMoney?amount={amount}", HttpMethod.PUT, null, OperationResponse.class, userId, amount);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        OperationResponse operationResponse = responseEntity.getBody();
        assertEquals(1, operationResponse.getCode());
    }

}