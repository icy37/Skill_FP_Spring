package com.example.skillfpspring;

import com.example.skillfpspring.entity.Operation;
import com.example.skillfpspring.entity.User;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class OperationTest {
    @Test
    public void testConstructor() {
        User user = new User();
        Operation operation = new Operation(user, Operation.OperationType.DEPOSIT, 10.0);
        assertEquals(user, operation.getUser());
        assertEquals(Operation.OperationType.DEPOSIT, operation.getOperationType());
        assertEquals(10.0, operation.getAmount());
    }
}