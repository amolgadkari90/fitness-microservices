package com.fitness.aiservice.service;

import java.util.List;

import com.fitness.aiservice.dto.RecomendationRequest;
import com.fitness.aiservice.dto.RecomendationResponse;
import com.fitness.aiservice.model.Recomendation;

public interface RecomentationService {

	List<RecomendationResponse> getRecomentationsByUserId(Long userId);


	List<RecomendationResponse> getRecomendationByactivityId(String activityId);
	
	
	Recomendation saveRecomendation(RecomendationRequest recomendationRequest);

}
