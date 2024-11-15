package org.example.chatgptspring08.controller;

import org.example.chatgptspring08.dto.odds.OddsRequest;
import org.example.chatgptspring08.dto.odds.OddsResponse;
<<<<<<< HEAD
import org.example.chatgptspring08.service.DebugCache;
import org.example.chatgptspring08.service.OddsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
=======
import org.example.chatgptspring08.service.OddsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

>>>>>>> 7485697c6acb859561298f7924802c2f966388d0
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@CrossOrigin("*")
@RestController
public class OddsApiController {
<<<<<<< HEAD

    @Autowired
    private OddsService oddsService;

    @Autowired
    DebugCache debugCache;

    @GetMapping("/football/odds/teams/{team}")
    public ResponseEntity<List<Map<String, Object>>> getOdds(@PathVariable String team) {
        List<OddsResponse> responseList = oddsService.fetchOddsFromApi();  // This will use caching
          debugCache.printCacheContent();  // for debuggin purposes. Will show the cached content.
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
=======
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
>>>>>>> 7485697c6acb859561298f7924802c2f966388d0
    }
}
