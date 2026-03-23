package com.fitness.activityservice.kafka;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fitness.events.ActivityEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
	
	private final KafkaTemplate<String, ActivityEvent<?>> kafkaTemplate;
	@Value("${kafka.topic.name}")
	private String topic;
	

    public void publish(String key, ActivityEvent<?> event) {
    		
    		log.info("KafkaProducer | publish | ActivityEvent.eventType: {} | topic: {}", event.eventType(), topic);    	
        kafkaTemplate.send(topic, key, event);
    }

}
