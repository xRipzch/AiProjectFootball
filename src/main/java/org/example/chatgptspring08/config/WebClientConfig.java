package org.example.chatgptspring08.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    final private String baseURL = "https://v3.football.api-sports.io";

    @Bean
    public WebClient.Builder webClient() {
        return WebClient.builder().baseUrl(baseURL);
    }

//    public Mono<String> getTeams
}
