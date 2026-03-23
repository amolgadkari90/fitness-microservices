package com.fitness.aiservice.dto;

import java.time.LocalDateTime;
import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RecomendationResponse {
	
	private String id;
	private String activityId;
	private String userId;
	private Analysis analysis;
	private List<String> improvements;
	private List<String> suggestions;
	private List<String> safety;
	private LocalDateTime createdAt;

}
