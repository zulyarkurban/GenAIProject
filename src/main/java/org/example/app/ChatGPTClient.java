package org.example.app;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

public class ChatGPTClient {
    private static final String API_KEY = System.getenv("AZURE_OPENAI_API_KEY");
    private static final String API_URL = System.getenv("AZURE_OPENAI_ENDPOINT");
    public String getTestCases(String userStory) throws Exception {
        if (userStory == null || userStory.isEmpty()) {
            throw new IllegalArgumentException("User story cannot be null or empty");
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
                .uri(new URI(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Extract the "content" field from the API response
        JsonNode responseJson = objectMapper.readTree(response.body());
        String content = responseJson.get("choices").get(0).get("message").get("content").asText();

        return content;
    }
}
