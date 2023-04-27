package com.example.greencoding;

import com.example.greencoding.atmservice.AtmService;
import com.example.greencoding.atmservice.RequestType;
import com.example.greencoding.atmservice.Task;
import com.example.greencoding.onlinegame.Clan;
import com.example.greencoding.onlinegame.GameRequest;
import com.example.greencoding.onlinegame.GameService;
import com.example.greencoding.transactions.Transaction;
import com.example.greencoding.transactions.TransactionController;
import com.example.greencoding.transactions.TransactionService;
import org.openjdk.jmh.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@State(Scope.Benchmark)
public class MyBenchmark {
    private AtmService atmService;
    private GameService gameService;
    private TransactionService transactionService;


    @Setup
    public void setup() {
        atmService = new AtmService();
        gameService = new GameService();
        transactionService = new TransactionService();
    }

    @Benchmark
    public void AtmServiceMethodBenchmark() {
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

    @Benchmark
    public void GameServiceMethodBenchmark() {
        int groupCount = 6;
        List<Clan> clans = List.of(
                new Clan(4, 50),
                new Clan(2, 70),
                new Clan(6, 60),
                new Clan(1, 15),
                new Clan(5, 40),
                new Clan(3, 45),
                new Clan(1, 12),
                new Clan(4, 40)
        );

        gameService.calculateEntranceOrder(new GameRequest(groupCount, clans));
    }


    @Benchmark
    public void TransactionServiceMethodBenchmark() {
        transactionService.generateReport(
                List.of(
                        new Transaction("32309111922661937852684864", "06105023389842834748547303", BigDecimal.valueOf(10.90)),
                        new Transaction("31074318698137062235845814", "66105036543749403346524547", BigDecimal.valueOf(200.90)),
                        new Transaction("66105036543749403346524547", "32309111922661937852684864", BigDecimal.valueOf(50.10)),
                        new Transaction("31074318698137062235845814", "32309111922661937852684864", BigDecimal.valueOf(100.10)),
                        new Transaction("31074318698137062235845814", "66105036543749403346524547", BigDecimal.valueOf(100.10))
                )
        );
    }
}
