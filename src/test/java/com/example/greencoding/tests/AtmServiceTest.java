package com.example.greencoding.tests;

import com.example.greencoding.atmservice.*;
import com.example.greencoding.transactions.Account;
import com.example.greencoding.transactions.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AtmServiceTest {

    AtmService atmService = new AtmService();


    @ParameterizedTest
    @MethodSource("atmTestData")
    public void testCalculateOrder(List<Task> tasks, List<Atm> expectedOutput) {
        // Make a request to the calculateOrder endpoint
        ResponseEntity<List<Atm>> response = atmService.calculateOrder(tasks);

        // Verify that the response is successful
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the response contains the expected ATMs in the correct order
        List<Atm> result = response.getBody();
        assertEquals(expectedOutput, result);
    }

    private static Stream<Arguments> atmTestData() {
        // First example test
        List<Task> tasks1 = Arrays.asList(
                new Task(4, RequestType.STANDARD, 1),
                new Task(1, RequestType.STANDARD, 1),
                new Task(2, RequestType.STANDARD, 1),
                new Task(3, RequestType.PRIORITY, 2),
                new Task(3, RequestType.STANDARD, 1),
                new Task(2, RequestType.SIGNAL_LOW, 1),
                new Task(5, RequestType.STANDARD, 2),
                new Task(5, RequestType.FAILURE_RESTART, 1)
        );
        List<Atm> expectedTasks1 = Arrays.asList(
                new Atm(1, 1),
                new Atm(2, 1),
                new Atm(3, 2),
                new Atm(3, 1),
                new Atm(4, 1),
                new Atm(5, 1),
                new Atm(5, 2)
        );

        // Second example test
        List<Task> tasks2 = Arrays.asList(
                new Task(1, RequestType.STANDARD, 2),
                new Task(1, RequestType.STANDARD, 1),
                new Task(2, RequestType.PRIORITY, 3),
                new Task(3, RequestType.STANDARD, 4),
                new Task(4, RequestType.STANDARD, 5),
                new Task(5, RequestType.PRIORITY, 2),
                new Task(5, RequestType.STANDARD, 1),
                new Task(3, RequestType.SIGNAL_LOW, 2),
                new Task(2, RequestType.SIGNAL_LOW, 1),
                new Task(3, RequestType.FAILURE_RESTART, 1)
        );
        List<Atm> expectedTasks2 = Arrays.asList(
                new Atm(1, 2),
                new Atm(1, 1),
                new Atm(2, 3),
                new Atm(2, 1),
                new Atm(3, 1),
                new Atm(3, 2),
                new Atm(3, 4),
                new Atm(4, 5),
                new Atm(5, 2),
                new Atm(5, 1)
        );

        List<Task> tasks3 = Arrays.asList(
                new Task(1, RequestType.PRIORITY, 1),
                new Task(2, RequestType.STANDARD, 2),
                new Task(1, RequestType.FAILURE_RESTART, 3),
                new Task(3, RequestType.SIGNAL_LOW, 4),
                new Task(2, RequestType.PRIORITY, 5),
                new Task(1, RequestType.STANDARD, 6)
        );
        List<Atm> expectedTasks3 = Arrays.asList(
                new Atm(1, 3),
                new Atm(1, 1),
                new Atm(1, 6),
                new Atm(2, 5),
                new Atm(2, 2),
                new Atm(3, 4)
        );

        // Empty Task list
        List<Task> tasks4 = Arrays.asList();
        List<Atm> expectedTasks4 = Arrays.asList();

        // All same tasks
        List<Task> tasks5 = Arrays.asList(
                new Task(1, RequestType.PRIORITY, 1),
                new Task(1, RequestType.PRIORITY, 1),
                new Task(1, RequestType.STANDARD, 1)
        );
        List<Atm> expectedTasks5 = Arrays.asList(
                new Atm(1, 1)
        );

        return Stream.of(
                Arguments.of(tasks1, expectedTasks1),
                Arguments.of(tasks2, expectedTasks2),
                Arguments.of(tasks3, expectedTasks3),
                Arguments.of(tasks4, expectedTasks4),
                Arguments.of(tasks5, expectedTasks5)
        );
    }
}
