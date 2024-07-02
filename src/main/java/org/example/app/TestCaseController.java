package org.example.app;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class TestCaseController {
    private ChatGPTClient chatGPTClient = new ChatGPTClient();

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/generate")
    public String generate(@RequestParam("userStory") String userStory, Model model) {
        try {
            if (userStory == null || userStory.trim().isEmpty()) {
                throw new IllegalArgumentException("User story cannot be null or empty");
            }
            String testCases = chatGPTClient.getTestCases(userStory);
            model.addAttribute("testCases", testCases);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("testCases", "Error generating test cases: " + e.getMessage());
        }
        return "index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveTestCases(@RequestParam("testCases") String testCases) {
        ExcelWriter excelWriter = new ExcelWriter();
        String filePath = "test_cases.xlsx"; // Specify your desired file path
        try {
            excelWriter.writeTestCasesToExcel(testCases, filePath);
            return "Test cases saved to " + filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error saving test cases: " + e.getMessage();
        }
    }
}

