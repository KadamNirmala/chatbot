package com.ai;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service

public class QnaService {

//	Access apikey and url
	@Value("${gemini.api.url}")
	private String geminiApiUrl;
	@Value("${gemini.api.key}")
	private String geminiApiKey;
	String apiUrl = geminiApiUrl + "?key=" + geminiApiKey;
	private final WebClient webClient;

    public QnaService(WebClient.Builder webClientBuilder) { 
        this.webClient = webClientBuilder.build();
    }
	
	public String getAnswer(String question) {
    Map<String, Object> requestBody = Map.of(
            "contents", new Object[]{
                    Map.of("parts", new Object[]{
                            Map.of("text", question)
                    })
            }
    );

    String apiUrl = geminiApiUrl + "?key=" + geminiApiKey; // Corrected API URL

    return webClient.post()
            .uri(apiUrl)
            .header("Content-Type", "application/json")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String.class)
            .block();
}

}
