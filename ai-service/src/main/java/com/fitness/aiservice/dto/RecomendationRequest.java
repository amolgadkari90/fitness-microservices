package com.fitness.aiservice.dto;

import java.util.List;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecomendationRequest {
	
	@NonNull
	private String activityId;
	@NonNull
	private Long userId;
	@NonNull
	private Analysis analysis;
	
	private List<String> improvements;
	
	private List<String> suggestions;
	
	private List<String> safety;
	

}
