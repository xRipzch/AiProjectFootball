package org.example.chatgptspring08.service;

import org.example.chatgptspring08.dto.odds.OddsResponse;
import org.springframework.beans.factory.annotation.Value;
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

    @Cacheable(value = "oddsCache", key = "'fullOddsResponse'")
    public List<OddsResponse> fetchOddsFromApi() {
        System.out.println("Fetching data from external API...");
        return oddsWebClient
                .get()
                .uri("/v4/sports/soccer_epl/odds?regions=eu&markets=h2h")
                .header("x-rapidapi-key", apiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OddsResponse>>() {})
                .block();
    }
}

