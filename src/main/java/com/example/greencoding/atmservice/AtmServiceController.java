package com.example.greencoding.atmservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class AtmServiceController {

    AtmService atmService;

    @Autowired
    public AtmServiceController(AtmService atmService) {
        this.atmService = atmService;
    }

    @PostMapping("/atms/calculateOrder")
    public ResponseEntity<List<Atm>> getCalculatedTaskOrder(@RequestBody List<Task> tasks) {
        return atmService.calculateOrder(tasks);
    }

}