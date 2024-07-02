package org.example.app;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestCaseGeneratorApp extends Application {
    private ChatGPTClient chatGPTClient = new ChatGPTClient();

    @Override
    public void start(Stage primaryStage) {
        TextArea userStoryInput = new TextArea();
        userStoryInput.setPromptText("Enter user story here...");

        Button generateButton = new Button("Generate Test Cases");
        TextArea testCaseOutput = new TextArea();
        testCaseOutput.setEditable(false);

        generateButton.setOnAction(event -> {
            try {
                String userStory = userStoryInput.getText();
                String testCases = chatGPTClient.getTestCases(userStory);
                testCaseOutput.setText(testCases);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox(userStoryInput, generateButton, testCaseOutput);
        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Test Case Generator");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}