package com.example.greencoding.tests;

import com.example.greencoding.onlinegame.Clan;
import com.example.greencoding.onlinegame.GameRequest;
import com.example.greencoding.onlinegame.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GameServiceTest {

    private final GameService gameService = new GameService();

    @ParameterizedTest
    @MethodSource("gameTestData")
    public void testCalculateEntranceOrder(GameRequest gameRequest, List<List<Clan>> expectedOrderedGroups) {
        ResponseEntity<List<List<Clan>>> response = gameService.calculateEntranceOrder(gameRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<List<Clan>> orderedGroups = response.getBody();

        assertEquals(expectedOrderedGroups.size(), orderedGroups.size());

        for (int i = 0; i < expectedOrderedGroups.size(); i++) {
            List<Clan> expectedGroup = expectedOrderedGroups.get(i);
            List<Clan> actualGroup = orderedGroups.get(i);

            assertEquals(expectedGroup.size(), actualGroup.size());

            for (int j = 0; j < expectedGroup.size(); j++) {
                Clan expectedClan = expectedGroup.get(j);
                Clan actualClan = actualGroup.get(j);

                assertEquals(expectedClan.getPoints(), actualClan.getPoints());
                assertEquals(expectedClan.getNumberOfPlayers(), actualClan.getNumberOfPlayers());
            }
        }
    }

    private static Stream<Arguments> gameTestData() {
        return Stream.of(
                // Example test
                Arguments.of(
                        createGameRequest(
                                6,
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
                        ),
                        List.of(
                                List.of(new Clan(2, 70), new Clan(4, 50)),
                                List.of(new Clan(6, 60)),
                                List.of(new Clan(3, 45), new Clan(1, 15), new Clan(1, 12)),
                                List.of(new Clan(4, 40)),
                                List.of(new Clan(5, 40))
                        )
                ),
                // Single clan
                Arguments.of(
                        createGameRequest(
                                3,
                                List.of(
                                        new Clan(4, 50)
                                )
                        ),
                        List.of(
                                List.of(new Clan(4, 50))
                        )
                )
        );
    }

    private static GameRequest createGameRequest(int groupCount, List<Clan> clans) {
        GameRequest gameRequest = new GameRequest();
        gameRequest.setGroupCount(groupCount);
        gameRequest.setClans(clans);
        return gameRequest;
    }


    @Test
    public void testCalculateEntranceOrderWithLargeClanList() {
        // create a list of 20000 clans with random points and number of players
        List<Clan> clans = new ArrayList<>();
        Random random = new Random();
        int maxPlayersPerGroup = random.nextInt(1000) + 1;
        int maxPointNumber = random.nextInt(100000) + 1;
        int numberOfClans = 20000;
        for (int i = 0; i < numberOfClans; i++) {
            int points = random.nextInt(maxPointNumber) + 1;
            int numberOfPlayers = random.nextInt(maxPlayersPerGroup) + 1;
            Clan clan = new Clan(numberOfPlayers, points);
            clans.add(clan);
        }

        // create a game request object and set the max number of players per group
        GameRequest gameRequest = new GameRequest();
        gameRequest.setGroupCount(maxPlayersPerGroup);
        gameRequest.setClans(clans);

        // call the method to calculate the entrance order and verify the result
        GameService gameService = new GameService();
        ResponseEntity<List<List<Clan>>> response = gameService.calculateEntranceOrder(gameRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<List<Clan>> orderedGroups = response.getBody();

        // verify that each group has at most maxPlayersPerGroup players
        for (List<Clan> group : orderedGroups) {
            int actualPlayersInGroup = 0;
            for (Clan clan : group) {
                actualPlayersInGroup += clan.getNumberOfPlayers();
            }
            assertTrue(actualPlayersInGroup <= maxPlayersPerGroup);
        }
    }
}
