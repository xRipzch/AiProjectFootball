package org.example.chatgptspring08.controller;

import org.example.chatgptspring08.model.ResponseWrapper;
import org.example.chatgptspring08.model.Team;
import org.example.chatgptspring08.model.TeamWrapper;
import org.example.chatgptspring08.service.TeamService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
public class FootballApiController {
    private final WebClient footballWebClient;
    private final TeamService teamService;

    public FootballApiController(WebClient.Builder webClientBuilder, TeamService teamService) {
        this.footballWebClient = webClientBuilder.baseUrl("https://v3.football.api-sports.io").build();
        this.teamService = teamService;
    }

    @Value("${sports.api.key}")
    private String apiKey;

    @GetMapping("/football/teams")
    public Mono<List<Team>> getTeams() {
        List<Team> allTeamsList = teamService.getAllTeams();

        // Populate allTeams list if empty
        if (teamService.getAllTeams().isEmpty()) {
            // If the list is empty, make an API call to fetch teams
            return footballWebClient
                .get()
                .uri("/teams?league=39&season=2022")
                .header("x-rapidapi-key", apiKey)
                .retrieve()
                .bodyToMono(ResponseWrapper.class)  // Deserialize the response into ResponseWrapper
                .map(response -> { // Extract the 'team' from each wrapper and collect them into a list
                    List<Team> teamsList = response.getResponse().stream()
                            .map(TeamWrapper::getTeam) // Extract the 'team' from each wrapper
                            .collect(Collectors.toList());
                    // Populate the teams in the service (store them in the database or in memory)
                    teamService.populateAllTeamsList(teamsList);
                    return teamsList;
                 });
        } else {
            return Mono.just(allTeamsList);
        }
    }

        @GetMapping("/football/h2h/{teamid1}-{teamid2}")
        public String head2head (@PathVariable int teamid1, @PathVariable int teamid2){
            // Retrieve raw data as a Map
            var response = footballWebClient.get()
                    .uri("/fixtures/headtohead?h2h=" + teamid1 + "-" + teamid2 + "&from=2024-01-01&to=2024-12-31")
                    .header("x-rapidapi-key", apiKey)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            // logs for error handling
            System.out.println("Raw Response: " + response);
            List<String> error = (List<String>) response.get("errors");
            System.out.println("API Errors: " + error);
            System.out.println(response);

            // Check if the response contains a "response" key and it is a list
            List<Map<String, Object>> responseList = (List<Map<String, Object>>) response.get("response");

            if (responseList != null && !responseList.isEmpty()) {
                // Loop through the matches
                StringBuilder headToHeadResults = new StringBuilder();

                for (Map<String, Object> headToHeadInfo : responseList) {
                    // Extract the fixture (match details)
                    Map<String, Object> fixture = (Map<String, Object>) headToHeadInfo.get("fixture");
                    Map<String, Object> teams = (Map<String, Object>) headToHeadInfo.get("teams");
                    Map<String, Object> score = (Map<String, Object>) headToHeadInfo.get("score");
                    Map<String, Object> goals = (Map<String, Object>) headToHeadInfo.get("goals");

                    // Extract team names correctly (teams are stored as Maps)
                    String team1Name = (String) ((Map<String, Object>) teams.get("home")).get("name");
                    String team2Name = (String) ((Map<String, Object>) teams.get("away")).get("name");

                    // Extract scores
                    int team1Score = (int) goals.get("home");
                    int team2Score = (int) goals.get("away");

                    // Extract match date
                    String matchDate = (String) fixture.get("date");

                    // Format the result
                    headToHeadResults.append(String.format("Match Date: %s\n", matchDate))
                            .append(String.format("Teams: %s vs %s\n", team1Name, team2Name))
                            .append(String.format("Score: %d - %d\n", team1Score, team2Score))
                            .append("\n");
                }

                return headToHeadResults.toString();
            } else {
                return "No data available for the given teams.";
            }
        }
    }
