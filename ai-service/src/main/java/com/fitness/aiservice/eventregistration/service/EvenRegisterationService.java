//package com.fitness.aiservice.eventregistration.service;
//
//import org.springframework.stereotype.Service;
//
//import com.fitness.aiservice.eventregistration.model.AiEventStatus;
//import com.fitness.aiservice.eventregistration.model.EventRegistry;
//import com.fitness.aiservice.eventregistration.repository.EventRegister;
//import com.fitness.events.ActivityCreatedEventPayload;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class EvenRegisterationService {
//	
//	private final EventRegister eventRegister;
//
//	public boolean saveEvent(ActivityCreatedEventPayload extractedPayload) {
//		
//		if (extractedPayload == null) {
//			throw new RuntimeException("Kafka event is null");
//		}
//		
//		EventRegistry eventRegistry = EventRegistry.builder()
//						.activityId(extractedPayload.activityId())
//						.userId(extractedPayload.userId())
//						.activityType(extractedPayload.activityType())
//						.duration(extractedPayload.duration())
//						.caloriesBurned(extractedPayload.caloriesBurned())
//						.startTime(extractedPayload.startTime())
//						.additionalMetrics(extractedPayload.additionalMetrics())
//						.status(AiEventStatus.NEW)
//						.build();
//		
//		
//		EventRegistry savedEvent = eventRegister.save(eventRegistry);
//		
//		if(savedEvent.getUserId() == extractedPayload.userId())
//			return true;
//		else 
//			return false;
//	}
//	
//	
//	
//
//}
