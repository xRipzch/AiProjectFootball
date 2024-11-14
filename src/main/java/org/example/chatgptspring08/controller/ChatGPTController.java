package org.example.chatgptspring08.controller;

import org.example.chatgptspring08.dto.chatgpt.ChatRequest;
import org.example.chatgptspring08.dto.chatgpt.ChatResponse;
import org.example.chatgptspring08.dto.chatgpt.Choice;
import org.example.chatgptspring08.dto.chatgpt.Usage;
import org.example.chatgptspring08.service.ChatGPTService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin("*")
@RestController
public class ChatGPTController {
    private final WebClient webClient;
    private final ChatGPTService chatGPTService;

    public ChatGPTController(WebClient.Builder webClientBuilder, ChatGPTService chatGPTService) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1/chat/completions").build();
        this.chatGPTService = chatGPTService;
    }

    // Return usage and choices for ChatGPT from sent odds data
    @PostMapping("/chat")
    public Map<String, Object> chatWithGPT(@RequestBody Map<String, Object> requestData) {
        String stringOddsResponse = requestData.get("message").toString();

        ChatRequest chatRequest = chatGPTService.fetchOddsSummary(stringOddsResponse);
        ChatResponse chatResponse = chatGPTService.promptChat(webClient, chatRequest);

        List<Choice> lst = chatResponse.getChoices();
        Usage usg = chatResponse.getUsage();

        Map<String, Object> map = new HashMap<>();
        map.put("Usage", usg);
        map.put("Choices", lst);

        return map;
    }
}
