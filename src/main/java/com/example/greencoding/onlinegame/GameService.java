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
        List<Clan> clans = new ArrayList<>(gameRequest.getClans());;

        // Validate input parameters
        if (groupCount <= 0 || clans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Sort clans by points in descending order and then by number of players in ascending order
        clans.sort(Comparator.comparingInt(Clan::getPoints).reversed()
                .thenComparingInt(Clan::getNumberOfPlayers));

        List<List<Clan>> entranceOrder = new ArrayList<>();

        while (!clans.isEmpty()) {
            List<Clan> group = createGroup(clans, groupCount);

            if (group == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            clans.removeAll(group);

            entranceOrder.add(group);
        }

        return new ResponseEntity<>(entranceOrder, HttpStatus.OK);
    }

    private List<Clan> createGroup(List<Clan> clans, int groupCount) {
        List<Clan> group = new ArrayList<>();
        int totalPlayersInGroup = 0;

        for (Clan clan : clans) {
            if (clan.getNumberOfPlayers() + totalPlayersInGroup <= groupCount) {
                group.add(clan);
                totalPlayersInGroup += clan.getNumberOfPlayers();
            }

            if (totalPlayersInGroup == groupCount) {
                break;
            }
        }

        if (group.isEmpty()) {
            return null;
        }

        // Sort the group by number of players in ascending order and then by points in descending order
        group.sort(Comparator.comparingInt(Clan::getPoints).reversed()
                .thenComparingInt(Clan::getNumberOfPlayers));

        return group;
    }
}
