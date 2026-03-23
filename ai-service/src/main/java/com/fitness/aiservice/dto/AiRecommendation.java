package com.fitness.aiservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AiRecommendation {
	private Analysis analysis;
    private List<Improvement> improvements;
    private List<Suggestion> suggestions;
    private List<String> safety;
}