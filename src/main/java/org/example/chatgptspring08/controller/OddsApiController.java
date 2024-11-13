package org.example.chatgptspring08.controller;

import org.example.chatgptspring08.dto.odds.OddsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
public class OddsApiController {
    private final WebClient oddsWebClient;

    public OddsApiController(WebClient.Builder webClientBuilder) {
        this.oddsWebClient = webClientBuilder.baseUrl("https://odds.p.rapidapi.com").build();
    }

    @Value("${odds.api.key}")
    private String apiKey;

    @GetMapping("/football/odds/teams/{team}")
    public ResponseEntity<List<Map<String, Object>>> getOdds(@PathVariable String team) {
        List<OddsResponse> responseList = oddsWebClient
                .get()
                .uri("/v4/sports/soccer_epl/odds?regions=eu&markets=h2h") // Premier league
                //.uri("/v4/sports/soccer_uefa_nations_league/odds?regions=eu") // Nations league
                .header("x-rapidapi-key", apiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OddsResponse>>() {})  // Expecting a list of OddsResponse
                .block();

        List<Map<String, Object>> results = new ArrayList<>();

        for (OddsResponse response : responseList) {
            String homeTeam = response.getHome_team().toLowerCase();
            String awayTeam = response.getAway_team().toLowerCase();

            if (homeTeam.contains(team.toLowerCase()) || awayTeam.contains(team.toLowerCase())) {
                Map<String, Object> map = new HashMap<>();
                map.put("Bookmaker", response.getBookmakers());
                map.put("HomeTeam", response.getHome_team());
                map.put("AwayTeam", response.getAway_team());
                map.put("DateTime", response.getCommence_time());
                results.add(map);
            }
        }
        return ResponseEntity.ok(results);
    }
}