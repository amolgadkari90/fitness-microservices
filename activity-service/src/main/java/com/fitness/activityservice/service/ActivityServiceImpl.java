package com.fitness.activityservice.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.enums.ResponseCode;
import com.fitness.activityservice.exceptions.ActivityNotFoundException;
import com.fitness.activityservice.kafka.KafkaProducer;
import com.fitness.activityservice.mapper.ActivityMapper;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import com.fitness.events.ActivityCreatedEventPayload;
import com.fitness.events.ActivityEvent;
import com.fitness.events.EventType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityServiceImpl implements ActivityService {

	private final ActivityRepository activityRespoitory;
	private final UserValidationService userValidationService;	
	private final KafkaProducer kafkaProducer;
	private final ActivityMapper mapper;
	


	@Override
	@Transactional
	public ActivityResponse trackActivity(ActivityRequest activityRequest) {
		
		log.info("ActivityService | trackActivity: {}", activityRequest.getUserId());
		
		Boolean validateUser = userValidationService.validateUser(activityRequest.getUserId());
		
//		// Check if null
//		if (activityRequest == null) {
//			throw new IllegalArgumentException("Activity request cannot be null");
//		}
		
		if(!validateUser) {
			throw new ActivityNotFoundException(ResponseCode.USER_4004.message());
		}

		// Map to Entity class
		Activity activity = mapper.requestDtoToEntity(activityRequest);

		// Save to Db

		Activity savedActivity = activityRespoitory.save(activity);

		// Map to response DTO

		ActivityResponse savedActivityResponse = mapper.entitytoResponseDto(savedActivity);
		
		// Generate Kafka Event
		ActivityCreatedEventPayload payload =
		        new ActivityCreatedEventPayload(
		                savedActivity.getId(),
		                savedActivity.getUserId(),
		                savedActivity.getType().name(),
		                savedActivity.getDuration(),
		                savedActivity.getCaloriesBurned(),
		                savedActivity.getStartTime(), 					// This is instant in the Kafka ActivtyEvent
		                savedActivity.getAdditionalMetrics()
		        );
		
		log.info("ActivityService | trackActivity | payload.activityId : {}", payload.activityId());

	    ActivityEvent<ActivityCreatedEventPayload> event =
	            ActivityEvent.create(
	                    EventType.ACTIVITY_CREATED,
	                    String.valueOf(savedActivity.getUserId()),
	                    payload
	            );
	    
	    log.info("ActivityService | trackActivity | ActivityEvent.eventId: {}", event.eventId());
	    
	    kafkaProducer.publish(event.userId(), event);


		// return

		return savedActivityResponse;
	}

	@Override
	@Transactional(readOnly = true)
	public ActivityResponse getActivityById(String id) {

		// Check id

		if (id == null) {
			throw new IllegalArgumentException(ResponseCode.USER_4001.message());
		}

		// find

		Activity activity = activityRespoitory.findById(id).get();

		// map to responseDto

		ActivityResponse entitytoResponseDto = mapper.entitytoResponseDto(activity);

		// Return
		return entitytoResponseDto;
	}

	@Override
	@Transactional
	public void deleteActivityById(String id) {
		// Check id

		if (id == null) {
			throw new IllegalArgumentException(ResponseCode.USER_4001.message());
		}
		
		//Find by id
		
		Activity activityById = activityRespoitory.findById(id).get();
		
		//if null exit
		if (activityById == null) {			
			throw new ActivityNotFoundException(ResponseCode.USER_4004.message());			
		}
		
		//If not null delete from DB
		activityRespoitory.deleteById(id);

	}

	@SuppressWarnings("unused")
	@Override
	public ActivityResponse updateActivity(ActivityRequest activityRequest) {
		
		log.info("Updating activity : {} ", activityRequest.getUserId());
		
		//Object is not null
		if (activityRequest == null) {
			throw new IllegalArgumentException(ResponseCode.USER_4001.message());
		}
		
		//Find by id - fetch
		
		log.info("Updating activity : {} ", activityRequest.getUserId());
		Activity fetchedActivity = activityRespoitory.findByUserId(activityRequest.getUserId()).get();
				
		//if not found exception
		
		if(fetchedActivity == null) {
			throw new ActivityNotFoundException(ResponseCode.USER_4004.message());
		}
		
		log.info("Activity found : {} ", fetchedActivity.getUserId());		
		
		//Map the object
		
		Activity updatedEntity = mapper.updateEntity(fetchedActivity, activityRequest);		
		log.info("Activity updated : {} ", updatedEntity.getUserId());
				
		//Save to DB
		
		Activity saved = activityRespoitory.save(updatedEntity);
		log.info("Activity saved : {} ", saved.getUserId());
		
		//Map saved to response
		
		ActivityResponse entitytoResponseDto = mapper.entitytoResponseDto(saved);
		log.info("Activity mapped : {} ", entitytoResponseDto.getUserId());
		
		return entitytoResponseDto;
	}

}
