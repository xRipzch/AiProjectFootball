package org.example.chatgptspring08.controller;

import org.example.chatgptspring08.dto.odds.OddsResponse;
import org.example.chatgptspring08.service.DebugCache;
import org.example.chatgptspring08.service.OddsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@CrossOrigin("*")
@RestController
public class OddsApiController {

    @Autowired
    private OddsService oddsService;

    @Autowired
    DebugCache debugCache;

    @GetMapping("/football/odds/teams/{team}")
    public ResponseEntity<List<Map<String, Object>>> getOdds(@PathVariable String team) {
        List<OddsResponse> responseList = oddsService.fetchOddsFromApi();  // This will use caching
          debugCache.printCacheContent();
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
