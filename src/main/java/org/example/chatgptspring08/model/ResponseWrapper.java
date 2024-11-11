package org.example.chatgptspring08.model;

import org.example.chatgptspring08.service.TeamService;

import java.util.List;

public class ResponseWrapper {
    private List<TeamWrapper> teamResponse; // A list of TeamWrapper objects
    private List<MatchWrapper> matchResponse; // A list of MatchWrapper objects


    public List<TeamWrapper> getResponse() {
        return teamResponse;
    }

    public void setTeamResponse(List<TeamWrapper> response) {
        this.teamResponse = response;
    }

    public List<MatchWrapper> getMatchResponse(){
        return matchResponse;
    }

    public void setMatchResponse(List<MatchWrapper> matchResponse){
        this.matchResponse = matchResponse;
    }
}