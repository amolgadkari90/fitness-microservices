//package com.fitness.aiservice.eventregistration.model;
//
//import java.time.LocalDateTime;
//import java.util.Map;
//
//import jakarta.persistence.Id;
//import jakarta.ws.rs.DefaultValue;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class EventRegistry {
//	
//	@Id
//	String activityId;	
//    Long userId;
//    String activityType;
//    Integer duration;
//    Integer caloriesBurned;
//    LocalDateTime startTime;
//    Map<String, Object> additionalMetrics;
//    
//    @DefaultValue(value = "NEW")
//    AiEventStatus status;   
//
//}
