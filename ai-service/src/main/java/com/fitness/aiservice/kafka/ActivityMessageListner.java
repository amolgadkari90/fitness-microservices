package com.fitness.aiservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.activityaiservice.ActivityAiService;
//import com.fitness.aiservice.dto.RecomendationRequest;
//import com.fitness.aiservice.eventregistration.service.EvenRegisterationService;
import com.fitness.events.ActivityCreatedEventPayload;
import com.fitness.events.ActivityEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityMessageListner {

	private final ObjectMapper objectMapper;
	private final ActivityAiService activityAiService;
	//private final EvenRegisterationService eventRegisteration;
	

	@KafkaListener(topics = "activity.events.v1", groupId = "activity-processor-group")
	public void activityConsumer(ActivityEvent<?> event, Acknowledgment ack) {

		log.info("ActivityMessageListner.activityConsumer {}", event.eventType());
		
		try {

	        switch (event.eventType()) {

	            case ACTIVITY_CREATED:

	                ActivityCreatedEventPayload payload =
	                        convertPayload(event.payload(), ActivityCreatedEventPayload.class);

	                log.info("Processing ACTIVITY_CREATED for activityId {}", payload.activityId());

	                try {
	                    // Wrap risky logic
	                    activityAiService.generateRecomendationFromGemini(payload);

	                } catch (Exception ex) {
	                    log.error("AI processing failed, skipping", ex);
	                }

	                break;

	            default:
	                log.warn("Unhandled event type {}", event.eventType());
	        }

	    } catch (Exception ex) {
	        log.error("Unexpected error in listener", ex);

	    } finally {
	        // ALWAYS ACK LAST
	        ack.acknowledge();
	    }
	}

	private <T> T convertPayload(Object payload, Class<T> type) {
		return objectMapper.convertValue(payload, type);
	}
}