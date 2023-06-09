package com.example.greencoding.atmservice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class AtmService {

    public ResponseEntity<List<Atm>> calculateOrder(List<Task> tasks) {
        // Use a more memory-efficient data structure for regionMap
        Map<Integer, LinkedList<Task>> regionMap = new HashMap<>();
        final int[] maxRegion = {0};
        // To avoid duplicate tasks in the result
        Set<Atm> addedAtms = new TreeSet<>(Comparator.comparing(Atm::getRegion).thenComparing(Atm::getAtmId));

        // Use parallel processing to sort the tasks by region number
        tasks.stream().sorted(Comparator.comparing(Task::getRegion)).forEachOrdered(task -> {
            Atm atm = new Atm(task.getRegion(), task.getAtmId());
            if (addedAtms.add(atm)) {
                int region = task.getRegion();
                maxRegion[0] = Math.max(region, maxRegion[0]);
                regionMap.computeIfAbsent(region, k -> new LinkedList<>()).add(task);
            }
        });

        // Use a single loop to handle all the tasks
        List<Atm> result = new ArrayList<>();
        for (int region = 1; region <= maxRegion[0]; region++) {
            if (!regionMap.containsKey(region)) {
                continue;
            }
            LinkedList<Task> regionTasks = regionMap.get(region);

            regionTasks.stream().filter(task -> task.getRequestType() == RequestType.FAILURE_RESTART)
                    .forEach(task -> result.add(new Atm(task.getRegion(), task.getAtmId())));
            regionTasks.stream().filter(task -> task.getRequestType() == RequestType.PRIORITY)
                    .forEach(task -> result.add(new Atm(task.getRegion(), task.getAtmId())));
            regionTasks.stream().filter(task -> task.getRequestType() == RequestType.SIGNAL_LOW)
                    .forEach(task -> result.add(new Atm(task.getRegion(), task.getAtmId())));
            regionTasks.stream().filter(task -> task.getRequestType() == RequestType.STANDARD)
                    .forEach(task -> result.add(new Atm(task.getRegion(), task.getAtmId())));
        }

        return ResponseEntity.ok(result);
    }
}
