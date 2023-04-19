package com.example.greencoding;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@RestController
public class ATMServiceController {
    @PostMapping("/atms/calculateOrder")
    public ResponseEntity<List<Atm>> calculateOrder(@RequestBody List<Task> tasks) {
        Map<Integer, PriorityQueue<Task>> regionMap = tasks.stream()
                .collect(Collectors.groupingBy(Task::getRegion,
                        Collectors.toCollection(PriorityQueue::new)));

        return ResponseEntity.ok(regionMap.entrySet().stream()
                .flatMap(entry -> sortByPriority(entry.getValue()).stream())
                .map(entry -> new Atm(entry.getRegion(), entry.getAtmId()))
                .collect(Collectors.toList()));
    }

    private List<Task> sortByPriority(PriorityQueue<Task> queue) {
        return queue.stream()
                .sorted(Comparator.comparing(Task::getRequestType))
                .collect(Collectors.toList());
    }
}