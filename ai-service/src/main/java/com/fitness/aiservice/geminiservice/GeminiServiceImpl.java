package com.fitness.aiservice.geminiservice;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.extern.slf4j.Slf4j;
import reactor.util.retry.Retry;

@Service
@Slf4j
public class GeminiServiceImpl implements GeminiService {

	private WebClient webClient;

	@Value("${Gemini.url}")
	private String geminiApiUrl;

	@Value("${Gemini.key}")
	private String geminiApiKey;

	public GeminiServiceImpl(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.build();
	}

	/*
	 * curl
	 * "https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent"
	 * \ -H "x-goog-api-key: $GEMINI_API_KEY" \ -H 'Content-Type: application/json'
	 * \ -X POST \ -d '{ "contents": [ { "parts": [ { "text":
	 * "Explain how AI works in a few words" } ] } ] }'
	 */

	public String sendAiRequest(String prompt) {
		
		log.info("Gemini URL: {}", geminiApiUrl);
		//log.info("Gemini key: {}", geminiApiKey);
		Map<String, Object> requestToGemini = Map.of("contents",
				List.of(Map.of("parts", List.of(Map.of("text", prompt)))));

		log.info("Request: {}", requestToGemini);

		String responseFromGemini = webClient.post()
											.uri(geminiApiUrl)
											.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
											.header("x-goog-api-key", geminiApiKey)
											//.header("Content-Type", "application/json")
											.bodyValue(requestToGemini)
											.retrieve()
											.bodyToMono(String.class)
											.retryWhen(
									                Retry.backoff(3, Duration.ofSeconds(30))
									                        .filter(ex -> ex instanceof WebClientResponseException)
									        )
											.block();

		return responseFromGemini;
	}
}
