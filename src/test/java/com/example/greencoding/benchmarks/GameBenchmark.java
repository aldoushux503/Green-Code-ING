package com.example.greencoding.benchmarks;

import com.example.greencoding.onlinegame.Clan;
import com.example.greencoding.onlinegame.GameRequest;
import com.example.greencoding.onlinegame.GameService;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import java.util.ArrayList;
import java.util.List;

public class GameBenchmark {
    private static final GameRequest GAME_REQUEST;
    GameService gameService;

    static {
        final ArrayList<Clan> clans = new ArrayList<>();
        for (int i = 0; i < 2_000; i++) {
            Clan clan1 = new Clan(4, 50);
            Clan clan2 = new Clan(100, 70);
            Clan clan3 = new Clan(6, 60);
            Clan clan4 = new Clan(210, 542);
            Clan clan5 = new Clan(200, 15000);
            Clan clan6 = new Clan(812, 45);
            Clan clan7 = new Clan(10, 500);

            clans.addAll(List.of(clan1, clan2, clan3, clan4, clan5, clan6, clan7));
        }
        GAME_REQUEST = new GameRequest(1000, clans);
    }

    @Setup
    public void setup() {
        gameService = new GameService();
    }

    @Benchmark
    public void GameServiceMethodBenchmark() {
        gameService.calculateEntranceOrder(GAME_REQUEST);
    }

}
