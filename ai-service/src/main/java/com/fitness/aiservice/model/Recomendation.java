package com.fitness.aiservice.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fitness.aiservice.dto.Analysis;

import lombok.Builder;
import lombok.Data;

@Document(collection = "recomendations")
@Data
@Builder
public class Recomendation {
	
	@Id
	private String id;
	
	@Indexed
	private String activityId;
	
	@Indexed
	private Long userId;
	
	private Analysis analysis;
	private List<String> improvements;
	private List<String> suggestions;
	private List<String> safety;

	@CreatedDate
	private LocalDateTime createdAt;

}
