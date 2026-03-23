package com.fitness.aiservice.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.aiservice.activityaiservice.ActivityAiService;
import com.fitness.aiservice.dto.ApiResponse;
import com.fitness.aiservice.dto.RecomendationResponse;
import com.fitness.aiservice.enums.ResponseCode;
import com.fitness.aiservice.model.Recomendation;
import com.fitness.aiservice.service.RecomentationService;
import com.fitness.events.ActivityCreatedEventPayload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/recomentations")
@RequiredArgsConstructor
@Slf4j
public class RecomentationController {
	
	private final RecomentationService recomentationService;
	
	private final ActivityAiService activityAiService;
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<ApiResponse<List<RecomendationResponse>>> getRecomendationByUserID(@PathVariable Long userId){
		log.info("RecomentationController | getRecomendationByUserID: {}", userId);
		List<RecomendationResponse> listOfUserRecomedations= recomentationService.getRecomentationsByUserId(userId);
		ApiResponse<List<RecomendationResponse>> success = ApiResponse.success(ResponseCode.USER_2000,listOfUserRecomedations);
		return ResponseEntity.ok().body(success);
	}
	
	@GetMapping("/activity/{activityId}")
	public ResponseEntity<ApiResponse<List<RecomendationResponse>>> getRecomendationByactivityID(@PathVariable String activityId){
		log.info("RecomentationController | getRecomendationByactivityID: {}", activityId);
		List<RecomendationResponse> listOfActivityRecomedations= recomentationService.getRecomendationByactivityId(activityId);
		ApiResponse<List<RecomendationResponse>> success = ApiResponse.success(ResponseCode.USER_2000,listOfActivityRecomedations);
		return ResponseEntity.ok().body(success);
	}
	
	@SuppressWarnings("unused")
	@GetMapping("/test")
	void testGetRecomendations(){
		
		ActivityCreatedEventPayload payload = new ActivityCreatedEventPayload(
		        "69a85ff851087f734e9a7ee9",
		        1L,
		        "WALKING",
		        45,
		        400,
		        LocalDateTime.now(),
		        Map.of(
		                "distanceKm", 5.2,
		                "avgHeartRate", 140
		        )
		);		
		
		Recomendation recomendationFromGemini = activityAiService.generateRecomendationFromGemini(payload);
		//log.info(recomendationFromGemini);
		
		
	}

}
