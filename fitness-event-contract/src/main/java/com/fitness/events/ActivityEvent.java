package com.fitness.events;

import java.time.Instant;
import java.util.UUID;

public record ActivityEvent<T>(
		String eventId,
        Instant timestamp,
        EventType eventType,
        String userId,
        T payload) 
{
	
	public static <T> ActivityEvent<T> create(
            EventType eventType,
            String userId,
            T payload) 
	{
        return new ActivityEvent<>(
                UUID.randomUUID().toString(),
                Instant.now(),
                eventType,
                userId,
                payload
        );
	}
}
