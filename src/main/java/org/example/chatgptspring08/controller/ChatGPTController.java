package org.example.chatgptspring08.controller;

import org.example.chatgptspring08.dto.*;
import org.example.chatgptspring08.service.FootballDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.example.chatgptspring08.service.FootballDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin("*")
@RestController
public class ChatGPTController {

    private final WebClient webClient;

    private final FootballDataService footballDataService;

    public ChatGPTController(WebClient.Builder webClientBuilder, FootballDataService footballDataService) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1/chat/completions").build();
        this.footballDataService = footballDataService;
    }

     @Value("${openai.api.key}")
     private String apiKey;
    @GetMapping("/chat/{message}")
    public Map<String, Object> chatWithGPT(@PathVariable String message) {
        ChatRequest chatRequest = new ChatRequest(); //ChatRequest objekt har jeg dannet med https://www.jsonschema2pojo.org/ værktøj
        chatRequest.setModel("gpt-3.5-turbo"); //vælg rigtig model. se powerpoint
        List<Message> lstMessages = new ArrayList<>(); //en liste af messages med roller
        lstMessages.add(new Message("system", "You are a helpful assistant."));
        lstMessages.add(new Message("user", "Where is " + message));
        chatRequest.setMessages(lstMessages);
        chatRequest.setN(1); //n er antal svar fra chatgpt
        chatRequest.setTemperature(1); //jo højere jo mere fantasifuldt svar (se powerpoint)
        chatRequest.setMaxTokens(10); //længde af svar
        chatRequest.setStream(false); //stream = true, er for viderekomne, der kommer flere svar asynkront
        chatRequest.setPresencePenalty(1); //noget med ikke at gentage sig. se powerpoint

        ChatResponse response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBearerAuth(apiKey))
                .bodyValue(chatRequest)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();

        List<Choice> lst = response.getChoices();
        Usage usg = response.getUsage();

        Map<String, Object> map = new HashMap<>();
        map.put("Usage", usg);
        map.put("Choices", lst);

        return map;
    }


    @GetMapping("/chatWithFootballData?team1={teamid1}&team2={teamid2}")
    public Map<String, Object> chatWithFootballData(@PathVariable int teamid1, @PathVariable int teamid2) {
        // Call the service layer to get the head-to-head results
        String headToHeadResults = footballDataService.getHeadToHeadResults(teamid1, teamid2);

        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setModel("gpt-3.5-turbo");

        List<Message> lstMessages = new ArrayList<>();
        lstMessages.add(new Message("system", "You are a helpful assistant specializing in sports statistics."));
        lstMessages.add(new Message("user", headToHeadResults)); // Use the result from the service

        chatRequest.setMessages(lstMessages);
        chatRequest.setN(1);
        chatRequest.setTemperature(1);
        chatRequest.setMaxTokens(100); // Allow more tokens for a detailed response
        chatRequest.setStream(false);
        chatRequest.setPresencePenalty(1);

        ChatResponse response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBearerAuth(apiKey))
                .bodyValue(chatRequest)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();

        List<Choice> lst = response.getChoices();
        Usage usg = response.getUsage();

        Map<String, Object> map = new HashMap<>();
        map.put("Usage", usg);
        map.put("Choices", lst);

        return map;
    }

}
