package com.fitness.activityservice.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.dto.ApiResponse;
import com.fitness.activityservice.enums.ResponseCode;
import com.fitness.activityservice.service.ActivityService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/activity")
@RequiredArgsConstructor
@Slf4j
public class ActivityController {
	
	private final ActivityService activityService;
	
	
	/*
	 * curl -X POST "http://localhost:8082/api/v1/activity/new" -H "Content-Type: application/json" -d "{\"userId\":\"user1\",\"type\":\"RUNNING\",\"duration\":45,\"caloriesBurned\":400,\"startTime\":\"2026-02-17T06:30:00\",\"additionalMetrics\":{\"distanceKm\":5.2,\"avgHeartRate\":140}}"
	 */
	
	@PostMapping("/new")
	public ResponseEntity<ApiResponse<ActivityResponse>> trackActivity(@Valid @RequestBody ActivityRequest activityRequest) {
		
		log.info("ActivityController | trackActivity");
		ActivityResponse activityResponse = activityService.trackActivity(activityRequest);	
		
		ApiResponse<ActivityResponse> registeredUser = ApiResponse.success(ResponseCode.USER_2001, activityResponse);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ActivityResponse>> getActivityById(@PathVariable String id) {
		
		ActivityResponse activityResponse = activityService.getActivityById(id);	
		
		ApiResponse<ActivityResponse> registeredUser = ApiResponse.success(ResponseCode.USER_2000, activityResponse);
		
		return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<ApiResponse<ActivityResponse>> updateActivity(@Valid @RequestBody ActivityRequest activityRequest) {
		
		ActivityResponse activityResponse = activityService.updateActivity(activityRequest);
		
		ApiResponse<ActivityResponse> updatedActivity = ApiResponse.success(ResponseCode.USER_2000, activityResponse);
		
		return ResponseEntity.status(HttpStatus.OK).body(updatedActivity);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<ActivityResponse>> deleteActivityById(@PathVariable String id) {
		
		activityService.deleteActivityById(id);	
		
		ApiResponse<ActivityResponse> registeredUser = ApiResponse.success(ResponseCode.USER_2000, null);
		
		return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
	}
	
	
	
	
	
	

}
