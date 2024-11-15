package org.example.chatgptspring08.service;

import org.example.chatgptspring08.dto.odds.OddsResponse;
import org.springframework.beans.factory.annotation.Value;
<<<<<<< HEAD
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class OddsService {
    private final WebClient oddsWebClient;

    public OddsService(WebClient.Builder webClientBuilder) {
        this.oddsWebClient = webClientBuilder.baseUrl("https://odds.p.rapidapi.com").build();
    }

    @Value("${odds.api.key}")
    private String apiKey;

    @Cacheable(value = "oddsCache", key = "'fullOddsResponse'")   // value= cache name, key= cache ke
    public List<OddsResponse> fetchOddsFromApi() {
        System.out.println("Fetching data from external API...");  // Will only be printed if odds data is not cached.
        return oddsWebClient   // Will fetch if not cached
                .get()
                .uri("/v4/sports/soccer_epl/odds?regions=eu&markets=h2h")
=======
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OddsService {
    @Value("${odds.api.key}")
    private String apiKey;

    // Create head-to-head API call to the odds webclient and return the response
    // - League: football league
    // - Region: of betting websites
    public List<OddsResponse> getOddsFromLeagueAndRegion(WebClient webClient, String league, String region) {
        return webClient
                .get()
                .uri("/v4/sports/" + league + "/odds?includeLinks=true&regions=" + region + "&markets=h2h")
>>>>>>> 7485697c6acb859561298f7924802c2f966388d0
                .header("x-rapidapi-key", apiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OddsResponse>>() {})
                .block();
    }
<<<<<<< HEAD
}

=======

    // Return a list of matches involving specified team
    public List<Map<String, Object>> getMatchesForTeam(List<OddsResponse> oddsResponseList, String team) {
        List<Map<String, Object>> filteredTeamList = new ArrayList<>();

        for (OddsResponse response : oddsResponseList) {
            String homeTeam = response.getHome_team().toLowerCase();
            String awayTeam = response.getAway_team().toLowerCase();

            if (homeTeam.contains(team.toLowerCase()) || awayTeam.contains(team.toLowerCase())) {
                Map<String, Object> map = new HashMap<>();
                map.put("Bookmaker", response.getBookmakers());
                map.put("HomeTeam", response.getHome_team());
                map.put("AwayTeam", response.getAway_team());
                map.put("DateTime", response.getCommence_time());
                filteredTeamList.add(map);
            }
        }
        return filteredTeamList;
    }
}
>>>>>>> 7485697c6acb859561298f7924802c2f966388d0
