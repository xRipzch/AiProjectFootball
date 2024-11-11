package org.example.chatgptspring08.service;

import org.example.chatgptspring08.controller.FootballApiController;
import org.springframework.stereotype.Service;

@Service
public class FootballDataService {

    private final FootballApiController footballApiController;

    public FootballDataService(FootballApiController footballApiController) {
        this.footballApiController = footballApiController;
    }

    public String getHeadToHeadResults(int teamid1, int teamid2) {

        return footballApiController.head2head(teamid1, teamid2);
    }
}
