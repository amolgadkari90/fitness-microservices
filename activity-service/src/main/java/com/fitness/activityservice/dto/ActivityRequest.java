package com.fitness.activityservice.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.fitness.activityservice.enums.ActivityType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ActivityRequest {
	
	@NotNull
	private Long userId;
	
	@NotNull
	private ActivityType type;
	
	@NotNull
	@Positive
	private Integer duration;
	
	@NotNull
	@Positive
	private Integer caloriesBurned;
	
	@NotNull
	private LocalDateTime startTime;
	
	private Map<String, Object> additionalMetrics;

}
