package org.example.chatgptspring08.controller;

import org.example.chatgptspring08.dto.odds.OddsRequest;
import org.example.chatgptspring08.dto.odds.OddsResponse;
import org.example.chatgptspring08.service.OddsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
public class OddsApiController {
    private final WebClient oddsWebClient;
    private final OddsService oddsService;

    public OddsApiController(WebClient.Builder webClientBuilder, OddsService oddsService) {
        this.oddsWebClient = webClientBuilder.baseUrl("https://odds.p.rapidapi.com").build();
        this.oddsService = oddsService;
    }

    // Receive head-to-head matches from specified league, region, and team
    // - Outdated method
    /*
    @GetMapping("/football/odds/h2h/leagues/{league}/regions/{region}/teams/{team}")
    public ResponseEntity<List<Map<String, Object>>> getOdds(@PathVariable String league, @PathVariable String region, @PathVariable String team) {
        List<OddsResponse> responseList = oddsService.getOddsFromLeagueAndRegion(oddsWebClient, league, region);
        List<Map<String, Object>> filteredMatchList = oddsService.getMatchesForTeam(responseList, team);
        return ResponseEntity.ok(filteredMatchList);
    }*/

    // Receive head-to-head matches using OddsRequest
    // - Receive data through @RequestBody instead of @PathVariable
    @PostMapping("/football/odds/h2h")
    public ResponseEntity<List<Map<String, Object>>> getOdds2(@RequestBody OddsRequest oddsRequest) {
        String league = oddsRequest.getLeague();
        String region = oddsRequest.getRegion();
        String team = oddsRequest.getTeam();

        List<OddsResponse> responseList = oddsService.getOddsFromLeagueAndRegion(oddsWebClient, league, region);
        List<Map<String, Object>> filteredMatchList = oddsService.getMatchesForTeam(responseList, team);
        return ResponseEntity.ok(filteredMatchList);
    }
}