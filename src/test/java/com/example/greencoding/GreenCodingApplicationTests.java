package com.example.greencoding;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GreenCodingApplicationTests {

    ATMServiceController atmServiceController = new ATMServiceController();

    @Test
    public void testCalculateOrderExample1() {
        // Create sample tasks
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(4, RequestType.STANDARD, 1));
        tasks.add(new Task(1, RequestType.STANDARD, 1));
        tasks.add(new Task(2, RequestType.STANDARD, 1));
        tasks.add(new Task(3, RequestType.PRIORITY, 2));
        tasks.add(new Task(3, RequestType.STANDARD, 1));
        tasks.add(new Task(2, RequestType.SIGNAL_LOW, 1));
        tasks.add(new Task(5, RequestType.STANDARD, 2));
        tasks.add(new Task(5, RequestType.FAILURE_RESTART, 1));

        // Make a request to the calculateOrder endpoint
        ResponseEntity<List<Atm>> response = atmServiceController.calculateOrder(tasks);

        // Verify that the response is successful
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the response contains the expected ATMs in the correct order
        List<Atm> result = response.getBody();
        assertEquals(7, result.size());
        assertEquals(new Atm(1, 1), result.get(0));
        assertEquals(new Atm(2, 1), result.get(1));
        assertEquals(new Atm(3, 2), result.get(2));
        assertEquals(new Atm(3, 1), result.get(3));
        assertEquals(new Atm(4, 1), result.get(4));
        assertEquals(new Atm(5, 1), result.get(5));
        assertEquals(new Atm(5, 2), result.get(6));
    }


    @Test
    public void testCalculateOrderExample2() {
        // Create sample tasks
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, RequestType.STANDARD, 2));
        tasks.add(new Task(1, RequestType.STANDARD, 1));
        tasks.add(new Task(2, RequestType.PRIORITY, 3));
        tasks.add(new Task(3, RequestType.STANDARD, 4));
        tasks.add(new Task(4, RequestType.STANDARD, 5));
        tasks.add(new Task(5, RequestType.PRIORITY, 2));
        tasks.add(new Task(5, RequestType.STANDARD, 1));
        tasks.add(new Task(3, RequestType.SIGNAL_LOW, 2));
        tasks.add(new Task(2, RequestType.SIGNAL_LOW, 1));
        tasks.add(new Task(3, RequestType.FAILURE_RESTART, 1));

        // Make a request to the calculateOrder endpoint
        ResponseEntity<List<Atm>> response = atmServiceController.calculateOrder(tasks);

        // Verify that the response is successful
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the response contains the expected ATMs in the correct order
        List<Atm> result = response.getBody();
        assertEquals(10, result.size());
        assertEquals(new Atm(1, 2), result.get(0));
        assertEquals(new Atm(1, 1), result.get(1));
        assertEquals(new Atm(2, 3), result.get(2));
        assertEquals(new Atm(2, 1), result.get(3));
        assertEquals(new Atm(3, 1), result.get(4));
        assertEquals(new Atm(3, 2), result.get(5));
        assertEquals(new Atm(3, 4), result.get(6));
        assertEquals(new Atm(4, 5), result.get(7));
        assertEquals(new Atm(5, 2), result.get(8));
        assertEquals(new Atm(5, 1), result.get(9));

    }

    @Test
    public void testCalculateOrder() {
        // Create sample tasks
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, RequestType.PRIORITY, 1));
        tasks.add(new Task(2, RequestType.STANDARD, 2));
        tasks.add(new Task(1, RequestType.FAILURE_RESTART, 3));
        tasks.add(new Task(3, RequestType.SIGNAL_LOW, 4));
        tasks.add(new Task(2, RequestType.PRIORITY, 5));
        tasks.add(new Task(1, RequestType.STANDARD, 6));

        // Make a request to the calculateOrder endpoint
        ResponseEntity<List<Atm>> response = atmServiceController.calculateOrder(tasks);

        // Verify that the response is successful
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the response contains the expected ATMs in the correct order
        List<Atm> result = response.getBody();
        assertEquals(6, result.size());
        assertEquals(new Atm(1, 3), result.get(0));
        assertEquals(new Atm(1, 1), result.get(1));
        assertEquals(new Atm(1, 6), result.get(2));
        assertEquals(new Atm(2, 5), result.get(3));
        assertEquals(new Atm(2, 2), result.get(4));
        assertEquals(new Atm(3, 4), result.get(5));
    }


    @Test
    public void testCalculateOrder_noTasks() {
        // Create an empty list of tasks
        List<Task> tasks = new ArrayList<>();

        // Make a request to the calculateOrder endpoint
        ResponseEntity<List<Atm>> response = atmServiceController.calculateOrder(tasks);

        // Verify that the response is successful and contains no ATMs
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).isEmpty());
    }

    @Test
    public void testCalculateOrder_allDuplicates() {
        // Create sample tasks that are all duplicates
        Task task1 = new Task(1, RequestType.PRIORITY, 1);
        Task task2 = new Task(1, RequestType.PRIORITY, 1);
        Task task3 = new Task(1, RequestType.STANDARD, 1);
        List<Task> tasks = Arrays.asList(task1, task2, task3);

        // Make a request to the calculateOrder endpoint
        ResponseEntity<List<Atm>> response = atmServiceController.calculateOrder(tasks);

        // Verify that the response is successful and contains only one ATM
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Atm> result = response.getBody();
        assertEquals(1, result.size());
        assertEquals(new Atm(1, 1), result.get(0));
    }
}
