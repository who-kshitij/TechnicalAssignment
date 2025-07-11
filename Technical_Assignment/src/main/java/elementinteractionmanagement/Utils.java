package elementinteractionmanagement;

import org.openqa.selenium.WebDriver;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.idealized.Javascript;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Utils {
    private static String API_URL="YOUR_API_URL";
    private static String API_KEY="YOUR_API_KEY";

    public static String[] translateToEnglish(WebDriver driver,String[] spanishSentences){
        JSONObject requestBody = new JSONObject();
        requestBody.put("from", "es");
        requestBody.put("to", "en");
        requestBody.put("e", "");
        requestBody.put("q", new JSONArray(spanishSentences));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://"+API_URL+"/t"))
                .header("Content-Type", "application/json")
                .header("x-rapidapi-key", API_KEY)
                .header("x-rapidapi-host", API_URL)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() == 200) {
            JSONArray responseArray = new JSONArray(response.body());
            String[] translatedTexts = new String[responseArray.length()];
            for (int i = 0; i < responseArray.length(); i++) {
                translatedTexts[i] = responseArray.getString(i);
                JavascriptExecutor jse = (JavascriptExecutor)driver;
                jse.executeScript("browserstack_executor: {\"action\": \"annotate\", \"arguments\": {\"data\": \""+
                        "Translated Title : "+translatedTexts[i]+
                        "\", \"level\": \"info\"}}");
            }
            return translatedTexts;
        } else {
            throw new RuntimeException("API call failed with status code: " + response.statusCode() +
                    " and body: " + response.body());
        }
    }


    public static void analyzeTranslatedHeaders(WebDriver driver, String[] translatedTitles) {
        Map<String, Integer> wordFrequency = new HashMap<>();
        boolean isRepeated=false;
        for (String title : translatedTitles) {
            String[] words = title.toLowerCase().split("\\W+");

            for (String word : words) {
                if (word.isEmpty()) {
                    continue;
                }
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }
        }
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            if (entry.getValue() > 2) {
                JavascriptExecutor jse = (JavascriptExecutor)driver;
                jse.executeScript("browserstack_executor: {\"action\": \"annotate\", \"arguments\": {\"data\": \""+
                        "Words repeated more than twice : "+entry.getKey() + ": " + entry.getValue()+
                        "\", \"level\": \"info\"}}");
                isRepeated=true;
            }
        }

        if(!isRepeated){
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("browserstack_executor: {\"action\": \"annotate\", \"arguments\": {\"data\": \""+
                    "No word in the translated titles was repeated more than two times."+
                    "\", \"level\": \"info\"}}");
        }

    }
}