package com.fitness.events;

import java.time.LocalDateTime;
import java.util.Map;

public record ActivityCreatedEventPayload(
		String activityId,
        Long userId,
        String activityType,
        Integer duration,
        Integer caloriesBurned,
        LocalDateTime startTime,
        Map<String, Object> additionalMetrics) {
}
