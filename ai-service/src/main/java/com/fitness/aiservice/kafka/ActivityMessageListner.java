package com.fitness.aiservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.activityaiservice.ActivityAiService;
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

    /**
     * Kafka Listener
     *
     * Now using direct POJO (ActivityEvent) since:
     * - Kafka JsonDeserializer is properly configured
     * - Config Server is correctly loading properties
     * - ack-mode is set to manual
     */
    @KafkaListener(topics = "activity.events.v1", groupId = "activity-processor-group")
    public void activityConsumer(ActivityEvent<?> event, Acknowledgment ack) {

        log.info("ActivityMessageListner.activityConsumer {}", event.eventType());

        try {

            switch (event.eventType()) {

                case ACTIVITY_CREATED:

                    /**
                     * Convert generic payload → specific DTO
                     */
                    ActivityCreatedEventPayload payload =
                            convertPayload(event.payload(), ActivityCreatedEventPayload.class);

                    log.info("Processing ACTIVITY_CREATED for activityId {}", payload.activityId());

                    try {
                        /**
                         * AI recommendation processing
                         */
                        activityAiService.generateRecomendationFromGemini(payload);

                    } catch (Exception ex) {
                        /**
                         * Prevent retry loop due to external service failure
                         */
                        log.error("AI processing failed, skipping", ex);
                    }

                    break;

                default:
                    /**
                     * Handle unknown event types safely
                     */
                    log.warn("Unhandled event type {}", event.eventType());
            }

        } catch (Exception ex) {
            /**
             * Catch unexpected runtime issues
             */
            log.error("Unexpected error in listener", ex);

        } finally {
            /**
             * Manual acknowledgment (critical for offset commit)
             */
            ack.acknowledge();
        }
    }

    /**
     * Utility to convert generic payload into typed DTO
     */
    private <T> T convertPayload(Object payload, Class<T> type) {
        return objectMapper.convertValue(payload, type);
    }
}