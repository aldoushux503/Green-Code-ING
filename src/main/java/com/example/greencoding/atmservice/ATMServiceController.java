package com.example.greencoding.atmservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ATMServiceController {

    AtmService atmService;

    @Autowired
    public ATMServiceController(AtmService atmService) {
        this.atmService = atmService;
    }

    @PostMapping("/atms/calculateOrder")
    public ResponseEntity<List<Atm>> getCalculatedTaskOrder(@RequestBody List<Task> tasks) {
        return atmService.calculateOrder(tasks);
    }

}