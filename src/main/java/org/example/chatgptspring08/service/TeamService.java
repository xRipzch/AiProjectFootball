package org.example.chatgptspring08.service;

import org.example.chatgptspring08.dto.team.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamService {
    private static final List<Team> allTeams = new ArrayList<>();
    private List<Team> tempList = new ArrayList<>();

    public void setList(Team team) {
        tempList.add(team);
    }

    public void populateAllTeamsOnce() {
        if (allTeams.isEmpty()) {
            allTeams.addAll(tempList);
        }
    }

    // Returns an unmodifiable view of the static allTeams list
    public static List<Team> getAllTeams() {
        return Collections.unmodifiableList(allTeams);
    }
}
