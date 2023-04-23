package com.example.greencoding.onlinegame;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GameService {


    public ResponseEntity<List<List<Clan>>> calculateEntranceOrder(GameRequest gameRequest) {
        int groupCount = gameRequest.getGroupCount();
        List<Clan> clans = gameRequest.getClans();

        // Sort clans by points in descending order
        clans.sort(Comparator.comparingInt(Clan::getPoints).reversed());

        List<List<Clan>> entranceOrder = new ArrayList<>();

        while (!clans.isEmpty()) {
            List<Clan> group = new ArrayList<>();
            int totalPlayersInGroup = 0;
            int totalPointsInGroup = 0;

            // Add as many clans as possible to the group
            for (Clan clan : clans) {
                if (clan.getNumberOfPlayers() + totalPlayersInGroup <= groupCount) {
                    group.add(clan);
                    totalPlayersInGroup += clan.getNumberOfPlayers();
                    totalPointsInGroup += clan.getPoints();
                }
            }

            // Remove clans from the list
            clans.removeAll(group);

            // Sort the group by the number of players in ascending order, then by points in descending order
            group.sort(Comparator.comparingInt(Clan::getNumberOfPlayers).thenComparingInt(Clan::getPoints).reversed());

            // Add the group to the entrance order
            entranceOrder.add(group);
        }

        return new ResponseEntity<>(entranceOrder, HttpStatus.OK);
    }
}
