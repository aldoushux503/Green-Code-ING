package com.example.greencoding.tests;

import com.example.greencoding.onlinegame.Clan;
import com.example.greencoding.onlinegame.GameRequest;
import com.example.greencoding.onlinegame.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GameServiceTest {

    private final GameService gameService = new GameService();

    @Test
    public void testExample() {
        GameRequest gameRequest = new GameRequest();
        gameRequest.setGroupCount(6);
        List<Clan> clans = new ArrayList<>(
                List.of(
                        new Clan(4, 50),
                        new Clan(2, 70),
                        new Clan(6, 60),
                        new Clan(1, 15),
                        new Clan(5, 40),
                        new Clan(3, 45),
                        new Clan(1, 12),
                        new Clan(4, 40)
                )
        );
        gameRequest.setClans(clans);

        ResponseEntity<List<List<Clan>>> response = gameService.calculateEntranceOrder(gameRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<List<Clan>> orderedGroups = response.getBody();
        assertTrue(orderedGroups.size() == 5);
        assertTrue(orderedGroups.get(0).size() == 2);
        assertTrue(orderedGroups.get(0).get(0).getPoints() == 70);
        assertTrue(orderedGroups.get(0).get(1).getPoints() == 50);
        assertTrue(orderedGroups.get(1).size() == 1);
        assertTrue(orderedGroups.get(1).get(0).getPoints() == 60);
        assertTrue(orderedGroups.get(2).size() == 3);
        assertTrue(orderedGroups.get(2).get(0).getPoints() == 45);
        assertTrue(orderedGroups.get(2).get(1).getPoints() == 15);
        assertTrue(orderedGroups.get(2).get(2).getPoints() == 12);
        assertTrue(orderedGroups.get(3).size() == 1);
        assertTrue(orderedGroups.get(3).get(0).getPoints() == 40);
        assertTrue(orderedGroups.get(4).size() == 1);
        assertTrue(orderedGroups.get(4).get(0).getPoints() == 40);
    }

    @Test
    void testWithSingleClan() {
        GameRequest request = new GameRequest();
        request.setGroupCount(3);
        Clan clan = new Clan();
        clan.setNumberOfPlayers(4);
        clan.setPoints(50);
        request.setClans(Collections.singletonList(clan));

        ResponseEntity<List<List<Clan>>> response = gameService.calculateEntranceOrder(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(Collections.singletonList(clan), response.getBody().get(0));
    }

    @Test
    public void testCalculateEntranceOrderWithLargeClanList() {

    }
}
