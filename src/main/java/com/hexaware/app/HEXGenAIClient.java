package com.hexaware.app;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatChoice;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestMessage;
import com.azure.ai.openai.models.ChatRequestSystemMessage;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.azure.ai.openai.models.ChatResponseMessage;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Component
public class HEXGenAIClient {
    private final String apiKey;
    private final String apiUrl;
    private final String apiModel;

    @Autowired
    public HEXGenAIClient(Environment environment) {
        this.apiKey = environment.getProperty("openai.api.key");
        System.out.println("API key: "+this.apiKey);
        this.apiUrl = environment.getProperty("openai.api.url");
        this.apiModel=environment.getProperty("openai.api.model");

    }
    public  String getTestCases(String userStory) throws Exception {
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
        try {
            OpenAIClient client = new OpenAIClientBuilder()
                    .endpoint(apiUrl)
                    .credential(new AzureKeyCredential(apiKey))
                    .buildClient();
            List<ChatRequestMessage> chatMessages = new ArrayList<>();
            chatMessages.add(new ChatRequestSystemMessage("You are a helpful assistant that generates test cases."));
            chatMessages.add(new ChatRequestUserMessage("Generate test cases for the following user story in Behavior driver format, don't add extra message \n:" + userStory));

            ChatCompletions chatCompletions = client.getChatCompletions(apiModel, new ChatCompletionsOptions(chatMessages));

            for (ChatChoice choice : chatCompletions.getChoices()) {
                ChatResponseMessage message = choice.getMessage();

                System.out.println("Message Length:"+message.getContent().length());
                String data=message.getContent();
              return data;
            }
        }catch (HttpClientErrorException e) {
            // Log and rethrow a more descriptive error
            throw new IllegalStateException("Error while calling the API: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        }
            return null;
    }
}
