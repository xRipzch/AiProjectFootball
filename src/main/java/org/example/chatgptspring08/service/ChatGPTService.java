package org.example.chatgptspring08.service;

import org.example.chatgptspring08.dto.chatgpt.ChatRequest;
import org.example.chatgptspring08.dto.chatgpt.ChatResponse;
import org.example.chatgptspring08.dto.chatgpt.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatGPTService {
    @Value("${openai.api.key}")
    private String apiKey;

    // Fetch a formatted summary of football match odds from JSON data
    public ChatRequest fetchOddsSummary(String message) {
        /* TO-DO: IMPLEMENT SITE LINK FOR EACH ODDS */
        final String promptManuscript = "You are given JSON data on football matches, including odds from multiple bookmakers for Team1, Team2, and Draw outcomes. For each match, follow these instructions strictly:\n" +
                "1. Convert the date and time of the match from UK format to Danish format (DD/MM/YYYY HH:MM, 24-hour clock).\n" +
                "2. Identify and select the highest odds for Team1, Draw, and Team2 outcomes across all bookmakers.\n" +
                "3. Calculate and select the highest for each outcome, and indicate the respective site.\n" +
                "4. Format the output precisely as follows:\n" +

                "Team1 - Team2 (DD/MM/YYYY HH:MM)\n" +
                "* Prediction: (Predicted Winner or Draw based on all of the options with the lowest odds)\n" +
                "* Team1: (Odds as a decimal) (Site with highest odds)\n" +
                "* Draw: (Odds as a decimal) (Site with highest draw odds)\n" +
                "* Team2: (Odds as a decimal) (Site with highest odds)\n\n" +

                "Example (Only use this as an example):\n" +
                "Ipswich Town - Manchester United (12/11/2024 21:00)\n" +
                "* Prediction: Manchester United\n" +
                "* Ipswich Town: 5.0 (Betfair)\n" +
                "* Draw: 4.4 (Betfair)\n" +
                "* Manchester United: 1.59 (Betclic)\n\n" +

                "Guidelines:\n" +
                "- Do not add extra commentary or format changes.\n" +
                "- Odds must be formatted in decimal (e.g., '4.0').\n" +
                "- Dates and times should be converted accurately from UK to Danish format.\n" +
                "- Accurately choose the odds for the specific site and if there are multiple sites list them all separated by commas.\n";

        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setModel("gpt-4o"); //vælg rigtig model. se powerpoint
        List<Message> lstMessages = new ArrayList<>(); //en liste af messages med roller
        lstMessages.add(new Message("system", "You are a helpful odds."));
        lstMessages.add(new Message("user",  promptManuscript + "\"" + message + "\""));
        chatRequest.setMessages(lstMessages);
        //chatRequest.setN(1); //n er antal svar fra chatgpt
        //chatRequest.setTemperature(1); //jo højere jo mere fantasifuldt svar (se powerpoint)
        //chatRequest.setMaxTokens(1000); //længde af svar
        chatRequest.setStream(false); //stream = true, er for viderekomne, der kommer flere svar asynkront
        chatRequest.setPresencePenalty(1); //noget med ikke at gentage sig. se powerpoint

        return chatRequest;
    }

    // Send a ChatRequest to ChatGPT's API and retrieve the response
    public ChatResponse promptChat(WebClient webClient, ChatRequest chatRequest) {
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBearerAuth(apiKey))
                .bodyValue(chatRequest)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();
    }
}
