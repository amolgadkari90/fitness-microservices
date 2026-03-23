package com.fitness.aiservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.fitness.aiservice.dto.AiRecommendation;
import com.fitness.aiservice.dto.Improvement;
import com.fitness.aiservice.dto.RecomendationRequest;
import com.fitness.aiservice.dto.RecomendationResponse;
import com.fitness.aiservice.dto.Suggestion;
import com.fitness.aiservice.model.Recomendation;

@Mapper(componentModel = "Spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface RecomentationMapper {
	
	
	// =========================
    // Existing mappings (KEEP)
    // =========================
    List<RecomendationResponse> entityToResponse(List<Recomendation> recomendations);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Recomendation requestToEntity(RecomendationRequest request);

    // =========================
    // NEW: AI → Request mapping
    // =========================
    @Mapping(target = "activityId", source = "activityId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "analysis", source = "ai.analysis")
    @Mapping(target = "improvements", expression = "java(mapImprovements(ai))")
    @Mapping(target = "suggestions", expression = "java(mapSuggestions(ai))")
    @Mapping(target = "safety", source = "ai.safety")
    RecomendationRequest aiToRequest(AiRecommendation ai, String activityId, Long userId);

    // =========================
    // Helper methods
    // =========================
//    default List<String> mapAnalysis(AiRecommendation ai) {
//        if (ai == null || ai.getAnalysis() == null) return List.of();
//
//        Analysis a = ai.getAnalysis();
//
//        return List.of(
//            "Overall: " + a.getOverall(),
//            "Pace: " + a.getPace(),
//            "HeartRate: " + a.getHeartRate(),
//            "Calories: " + a.getCaloriesBurned()
//        );
//    }

    default List<String> mapImprovements(AiRecommendation ai) {
        if (ai == null || ai.getImprovements() == null) return List.of();

        return ai.getImprovements()
                .stream()
                .map(Improvement::getRecommendation)
                .toList();
    }

    default List<String> mapSuggestions(AiRecommendation ai) {
        if (ai == null || ai.getSuggestions() == null) return List.of();

        return ai.getSuggestions()
                .stream()
                .map(Suggestion::getDescription)
                .toList();
    }

}
