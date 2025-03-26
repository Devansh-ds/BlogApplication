package com.blog.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class HuggingFaceSummarizationService {

    private final WebClient webClient;

    @Value("${huggingface.api.key}")
    private String apiKey;

    @Value("${huggingface.api.url}")
    private String apiUrl;

    public HuggingFaceSummarizationService() {
        this.webClient = WebClient.builder().build();
    }

    public String summarizeText(String text) {
        text = "Make it small and summarize: " + text;
        Map<String, Object> requestBody = Map.of("inputs", text);

        List<Map<String, String>> response = webClient.post()
                .uri(apiUrl)
                .header("Authorization", apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(List.class)
                .block();  // Blocking call, can be converted to async if needed

        if (response != null && !response.isEmpty()) {
            return response.get(0).get("summary_text");
        }

        return "Summarization failed. Try again.";
    }
}
