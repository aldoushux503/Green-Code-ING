package com.example.greencoding.onlinegame;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    public ResponseEntity<List<List<Clan>>> calculateEntranceOrder(GameRequest gameRequest) {
        List<List<Clan>> orderedGroups = new ArrayList<>();
        int maxPlayersPerGroup = gameRequest.getGroupCount();

        List<Clan> clans = new ArrayList<>(gameRequest.getClans());
        clans.sort(Comparator.comparingInt(Clan::getPoints).reversed()
                .thenComparingInt(Clan::getNumberOfPlayers));

        while (!clans.isEmpty()) {
            List<Clan> group = new ArrayList<>();
            int actualPlayersInGroup = 0;
            Iterator<Clan> iter = clans.iterator();

            while (iter.hasNext() && actualPlayersInGroup < maxPlayersPerGroup) {
                Clan next = iter.next();
                if (actualPlayersInGroup + next.getNumberOfPlayers() <= maxPlayersPerGroup) {
                    actualPlayersInGroup += next.getNumberOfPlayers();
                    group.add(next);
                    iter.remove();
                }
            }

            orderedGroups.add(group);
        }
        return new ResponseEntity<>(orderedGroups, HttpStatus.OK);
    }
}
