package com.example.greencoding.onlinegame;

import java.util.List;

public class GameRequest {
    private int groupCount;
    private List<Clan> clans;

    public GameRequest() {
    }

    public GameRequest(int groupCount, List<Clan> clans) {
        this.groupCount = groupCount;
        this.clans = clans;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public List<Clan> getClans() {
        return clans;
    }

    public void setClans(List<Clan> clans) {
        this.clans = clans;
    }
}