package com.skilltrack.skilltrack_backend.controller;

import com.skilltrack.skilltrack_backend.dto.AiChatRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody AiChatRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gemma3:4b");
        body.put("prompt", buildPrompt(request.getMessage()));
        body.put("stream", false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://localhost:11434/api/generate",
                entity,
                Map.class
        );

        String aiResponse = response.getBody().get("response").toString();

        return ResponseEntity.ok(aiResponse);
    }

    private String buildPrompt(String userMessage) {
        return """
    You are SkillTracker AI, a helpful assistant inside a skills tracking app.

    Rules:
    - Respond directly to the user's message
    - Do NOT ask the same question repeatedly
    - If the user states a goal, help them immediately
    - Be concise and practical

    User message:
    %s
    """.formatted(userMessage);
    }

}
