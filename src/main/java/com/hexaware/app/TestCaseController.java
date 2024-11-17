package com.hexaware.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;

@Controller
public class TestCaseController {

    private final HEXGenAIClient chatGPTClient;

    @Autowired
    public TestCaseController(HEXGenAIClient chatGPTClient) {
        this.chatGPTClient = chatGPTClient;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Clear any previous attributes
        model.addAttribute("testCases", "");
        model.addAttribute("message", "");
        model.addAttribute("errorMessage", false);
        model.addAttribute("saved", false);  // Indicate that nothing has been saved yet
        return "index";
    }

    @PostMapping("/generate")
    public String generate(@RequestParam("userStory") String userStory, Model model) {
        try {
            if (userStory == null || userStory.trim().isEmpty()) {
                throw new IllegalArgumentException("User story cannot be null or empty");
            }
            String testCases = chatGPTClient.getTestCases(userStory).toString();
            model.addAttribute("testCases", testCases);
            model.addAttribute("saved", false);  // Reset saved status
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("testCases", "Error generating test cases: " + e.getMessage());
        }
        return "index";
    }

    @PostMapping("/save")
    public String saveTestCases(@RequestParam("testCases") String testCases, Model model) {
        if (testCases == null || testCases.trim().isEmpty()) {
            model.addAttribute("message", "Test cases input cannot be empty.");
            model.addAttribute("saved", false);
            return "index";
        }

        String filePath = "test_cases.xlsx"; // Could be made configurable
        try {
            ExcelWriter excelWriter = new ExcelWriter();
            excelWriter.writeTestCasesToExcel(testCases, filePath);

            model.addAttribute("message", "Test cases successfully saved to " + filePath);
            model.addAttribute("saved", true);
        } catch (IOException e) {
            model.addAttribute("message", "Error saving test cases: " + e.getMessage());
            model.addAttribute("saved", false);
        }

        model.addAttribute("testCases", testCases);
        return "index";
    }

}