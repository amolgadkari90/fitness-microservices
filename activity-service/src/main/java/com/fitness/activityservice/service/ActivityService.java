package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;

import jakarta.validation.Valid;

public interface ActivityService {

	public ActivityResponse trackActivity(ActivityRequest activityRequest);

	public ActivityResponse getActivityById(String id);

	public void deleteActivityById(String id);

	public ActivityResponse updateActivity(@Valid ActivityRequest activityRequest);

}
