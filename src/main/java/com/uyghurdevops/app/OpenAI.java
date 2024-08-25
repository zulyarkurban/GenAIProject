package com.uyghurdevops.app;
import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatChoice;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestMessage;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.azure.ai.openai.models.ChatResponseMessage;
import com.azure.identity.DefaultAzureCredentialBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OpenAI {
    public static void main(String[] args) {
        String endpoint = System.getenv("AZURE_OPENAI_ENDPOINT");
        String deploymentName = System.getenv("AZURE_DEPLOYMENT_NAME");
        OpenAIClientBuilder builder = new OpenAIClientBuilder()
                .endpoint(endpoint)
                .credential(new DefaultAzureCredentialBuilder().build());
        OpenAIClient client = builder.buildClient();
        List<ChatRequestMessage> prompts = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("What do you want to search: ");
        String userInput = scanner.nextLine();
        prompts.add(new ChatRequestUserMessage(userInput));

        ChatCompletions chatCompletions = client.getChatCompletions(deploymentName, new ChatCompletionsOptions(prompts));

        for (ChatChoice choice : chatCompletions.getChoices()) {

            ChatResponseMessage message = choice.getMessage();
            System.out.printf("Index: %d, Chat Role: %s.%n", choice.getIndex(), message.getRole());
            System.out.println("Message:");
            System.out.println(message.getContent());
        }

        System.out.println();
    }
}