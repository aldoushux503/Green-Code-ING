package com.example.greencoding;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ATMServiceController {
    @PostMapping("/atms/calculateOrder")
    public ResponseEntity<List<Atm>> calculateOrder(@RequestBody List<Task> tasks) {
        // Sort tasks by region number
        Collections.sort(tasks, Comparator.comparing(Task::getRegion));

        // Create a map to store tasks by region number
        Map<Integer, List<Task>> regionMap = new HashMap<>();
        // To avoid duplicate tasks in the result
        Set<Atm> addedAtms = new TreeSet<>(Comparator.comparing(Atm::getRegion).thenComparing(Atm::getAtmId));

        for (Task task : tasks) {
            int region = task.getRegion();
            Atm atm = new Atm(task.getRegion(), task.getAtmId());
            if (addedAtms.add(atm)) {
                regionMap.computeIfAbsent(region, k -> new ArrayList<>()).add(task);
            }
        }


        List<Atm> result = new ArrayList<>();
        for (int region = 1; region <= 5; region++) {
            if (!regionMap.containsKey(region)) {
                continue;
            }
            List<Task> regionTasks = regionMap.get(region);
            List<Task> priorityTasks = new ArrayList<>();
            List<Task> standardTasks = new ArrayList<>();
            Task failureRestartTask = null;
            Task signalLowTask = null;
            for (Task task : regionTasks) {
                if (task.getRequestType() == RequestType.PRIORITY) {
                    priorityTasks.add(task);
                } else if (task.getRequestType() == RequestType.STANDARD) {
                    standardTasks.add(task);
                } else if (task.getRequestType() == RequestType.FAILURE_RESTART) {
                    failureRestartTask = task;
                } else if (task.getRequestType() == RequestType.SIGNAL_LOW) {
                    signalLowTask = task;
                }
            }
            // Handle the failure restart task first, if present
            if (failureRestartTask != null) {
                result.add(new Atm(failureRestartTask.getRegion(), failureRestartTask.getAtmId()));
            }
            // Handle the priority tasks next
            for (Task task : priorityTasks) {
                result.add(new Atm(task.getRegion(), task.getAtmId()));
            }
            // Handle the signal low task, if present, after the priority tasks
            if (signalLowTask != null) {
                result.add(new Atm(signalLowTask.getRegion(), signalLowTask.getAtmId()));
            }
            // Handle the standard tasks last
            for (Task task : standardTasks) {
                result.add(new Atm(task.getRegion(), task.getAtmId()));
            }
        }

        return ResponseEntity.ok(result);
    }

}