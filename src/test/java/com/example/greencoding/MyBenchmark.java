package com.example.greencoding;

import com.example.greencoding.atmservice.AtmService;
import com.example.greencoding.atmservice.RequestType;
import com.example.greencoding.atmservice.Task;
import org.openjdk.jmh.annotations.*;

import java.util.List;

@State(Scope.Benchmark)
public class MyBenchmark {
    private AtmService atmService;


    @Setup
    public void setup() {
        atmService = new AtmService();
    }

    @Benchmark
    public void myMethodBenchmark() {
        atmService.calculateOrder(
                List.of(
                        new Task(4, RequestType.STANDARD, 1),
                        new Task(1, RequestType.STANDARD, 1),
                        new Task(2, RequestType.STANDARD, 1),
                        new Task(3, RequestType.PRIORITY, 2),
                        new Task(3, RequestType.STANDARD, 1),
                        new Task(2, RequestType.SIGNAL_LOW, 1),
                        new Task(5, RequestType.FAILURE_RESTART, 1),
                        new Task(5, RequestType.PRIORITY, 2),
                        new Task(1, RequestType.STANDARD, 3),
                        new Task(5, RequestType.FAILURE_RESTART, 5),
                        new Task(3, RequestType.STANDARD, 3),
                        new Task(4, RequestType.STANDARD, 4)
                )
        );
    }
}
