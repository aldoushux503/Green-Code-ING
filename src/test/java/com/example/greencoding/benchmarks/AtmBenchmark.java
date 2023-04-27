package com.example.greencoding.benchmarks;


import com.example.greencoding.atmservice.AtmService;
import com.example.greencoding.atmservice.RequestType;
import com.example.greencoding.atmservice.Task;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class AtmBenchmark {
    private AtmService atmService;
    private static final List<Task> TASKS;

    static {
        TASKS = new ArrayList<>();
        for (int i = 1; i <= 5_000; i++) {
            Task task1 = new Task(i + 1, RequestType.STANDARD, i+2);
            Task task2 = new Task(i + 2, RequestType.SIGNAL_LOW, i+1);
            Task task3 = new Task(i % 2 + 1, RequestType.PRIORITY, i * 2);
            Task task4 = new Task(i , RequestType.FAILURE_RESTART, i);
            TASKS.addAll(List.of(task1, task2, task3, task4));
        }
    }

    @Setup
    public void setup() {
        atmService = new AtmService();
    }
    @Benchmark
    public void AtmServiceMethodBenchmark() {
        atmService.calculateOrder(TASKS);
    }


}
