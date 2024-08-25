package com.uyghurdevops.app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ChatGPTClient {

    private final String apiKey;
    private final String apiUrl;

    @Autowired
    public ChatGPTClient(Environment environment) {
        this.apiKey = environment.getProperty("chatgpt.api.key");
        this.apiUrl = environment.getProperty("chatgpt.api.url");
    }

    public String getTestCases(String userStory) throws Exception {
        if (userStory == null || userStory.isEmpty()) {
            throw new IllegalArgumentException("User story cannot be null or empty");
        }

        // Handle potential null values
        if (this.apiKey == null || this.apiKey.isEmpty()) {
            throw new IllegalStateException("API Key is not configured.");
        }
        if (this.apiUrl == null || this.apiUrl.isEmpty()) {
            throw new IllegalStateException("API URL is not configured.");
        }

        HttpClient client = HttpClient.newHttpClient();
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a helpful assistant that generates test cases."),
                        Map.of("role", "user", "content", "Generate test cases for the following user story: " + userStory)
                )
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBodyJson = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(this.apiUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + this.apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Extract the "content" field from the API response
        JsonNode responseJson = objectMapper.readTree(response.body());
        String content = responseJson.get("choices").get(0).get("message").get("content").asText();

        return content;
    }
}
