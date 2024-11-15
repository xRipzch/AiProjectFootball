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
        final String promptManuscript = "\"You are given JSON data on football matches, including odds from multiple bookmakers for Team1, Team2, and Draw outcomes. For each match, follow these instructions strictly:\\n\\n1. Parse the JSON data and extract the relevant details for each match, including the team names (Team1 and Team2) and the odds for each outcome (Team1 win, Draw, Team2 win) from all bookmakers.\\n2. Convert the date and time of the match from UK format to Danish format (DD/MM/YYYY HH:MM, 24-hour clock).\\n3. Identify and select the highest odds for each outcome (Team1, Draw, Team2) across all bookmakers.\\n4. Accurately indicate the respective site(s) offering the highest odds for each outcome.\\n5. Ensure that the odds in the output are taken directly from the JSON data. Do not invent or modify the odds.\\n6. Output the response strictly as plain HTML (no ```html or ``` markers).\\n7. The HTML must follow this precise structure:\\n\\n<div class=\\\"match\\\">\\n  <h1 class=\\\"team-names\\\">[Team1] - [Team2] (DD/MM/YYYY HH:MM)</h1>\\n  <ul class=\\\"odds-predictions\\\">\\n    <li><strong>Prediction:</strong> (Predicted Winner or Draw based on the lowest odds)</li>\\n    <li><strong>[Team1]:</strong> (Highest Odds as a decimal) (Site(s) offering the highest odds)</li>\\n    <li><strong>Draw:</strong> (Highest Odds as a decimal) (Site(s) offering the highest odds)</li>\\n    <li><strong>[Team2]:</strong> (Highest Odds as a decimal) (Site(s) offering the highest odds)</li>\\n  </ul>\\n</div>\\n\\nExample Output:\\n\\n<div class=\\\"match\\\">\\n  <h1 class=\\\"team-names\\\">Ipswich Town - Manchester United (12/11/2024 21:00)</h1>\\n  <ul class=\\\"odds-predictions\\\">\\n    <li><strong>Prediction:</strong> Manchester United</li>\\n    <li><strong>Ipswich Town:</strong> 5.0 (Betfair)</li>\\n    <li><strong>Draw:</strong> 4.4 (Betfair)</li>\\n    <li><strong>Manchester United:</strong> 1.59 (Betclic)</li>\\n  </ul>\\n</div>\\n\\nGuidelines:\\n- Replace [Team1] and [Team2] with the actual team names from the JSON data.\\n- All odds must be extracted directly from the provided JSON data, and the sites listed must match the sites offering those odds.\\n- All responses must use plain HTML strictly as defined above, without ```html or ``` markers.\\n- Dates and times must be converted from UK format to Danish format accurately.\\n- Ensure all odds are formatted as decimals (e.g., \\\"4.0\\\").\\n- For each outcome ([Team1], Draw, [Team2]), if multiple sites have the highest odds, list all sites separated by commas.\\n- No extra commentary, no changes to the structure, and no additional text outside of the specified HTML format.\\n- It is critical that the odds and sites are accurate and match the data provided in the JSON. Do not fabricate or make up odds or site names under any circumstances.\"\n";

        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setModel("gpt-4o"); //vælg rigtig model. se powerpoint
        List<Message> lstMessages = new ArrayList<>(); //en liste af messages med roller
        lstMessages.add(new Message("system", "You are a helpful odds."));
        lstMessages.add(new Message("user",  promptManuscript + "\"" + message + "\""));
        chatRequest.setMessages(lstMessages);
        //chatRequest.setN(1); //n er antal svar fra chatgpt
        chatRequest.setTemperature(1); //jo højere jo mere fantasifuldt svar (se powerpoint)
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
