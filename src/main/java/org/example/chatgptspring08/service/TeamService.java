package org.example.chatgptspring08.service;

import org.example.chatgptspring08.model.Team;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TeamService { // Move to JS?
    private static List<Team> allTeams = Collections.synchronizedList(new ArrayList<>()); // Thread safe operations...

    public void addTeamToAllTeamsList(Team footballTeam){
        allTeams.add(footballTeam);
    }

    public void populateAllTeamsList(List<Team> teamList) {
        allTeams = teamList;
    }

    public List<Team> getAllTeams() {
        return new ArrayList<>(allTeams);
    }

}
