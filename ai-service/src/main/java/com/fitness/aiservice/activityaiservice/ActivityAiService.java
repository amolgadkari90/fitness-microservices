package com.fitness.aiservice.activityaiservice;

import org.springframework.stereotype.Service;
import com.fitness.events.ActivityCreatedEventPayload;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.dto.AiRecommendation;
import com.fitness.aiservice.dto.RecomendationRequest;
import com.fitness.aiservice.geminiservice.GeminiService;
import com.fitness.aiservice.mapper.RecomentationMapper;
import com.fitness.aiservice.model.Recomendation;
import com.fitness.aiservice.service.RecomentationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j

public class ActivityAiService {
	
	public final GeminiService geminiService;
	private final RecomentationMapper recomentationMapper;
	
	private final RecomentationService recomendationService;
	
	public Recomendation generateRecomendationFromGemini(ActivityCreatedEventPayload activity) {
		
		//create prompt
		String prompt = createPromptForActivity(activity);
		log.info("ActivityAiService | generateRecomendationFromGemini.prompt: {}",prompt);
		
		//Wrap prompt in Gemini JSON format
		String aiResponse = geminiService.sendAiRequest(prompt);
		log.info("ActivityAiService | generateRecomendationFromGemini.aiRequest: {}", aiResponse);
		
		// ============================================================
        // Now we capture response instead of directly returning
        // ============================================================
        RecomendationRequest request = processAiResponse(activity, aiResponse);

        	// ============================================================
        // Safety check (avoid saving null)
        // ============================================================
        if (request == null) {
            log.warn("AI response parsing failed, skipping DB save");
            return null;
        }
        
        // ============================================================
        // Save to MongoDB
        // ============================================================
        Recomendation saveRecomendation = recomendationService.saveRecomendation(request);
		
		return saveRecomendation;
	}

	private RecomendationRequest processAiResponse(ActivityCreatedEventPayload activity, String aiResponse) {
		log.info("ActivityAiService | processAiResponse ");
		RecomendationRequest aiRecomendation;
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(aiResponse);
			
			
			/*
			 * {
			 * "candidates": [
    			 *		{
      					"content": {
        							"parts": [
          							{
            								"text": 
        								],
      								},      
    					}  
				}
			 * */
			
			String aiJson = rootNode.path("candidates")
										.get(0)
										.path("content")
										.path("parts")
										.get(0)
										.path("text")
										.asText();
			
			
//			String jsonContent = textNode.asText()
//					.replaceAll("\\n  \\", "")
//					.replaceAll("\\n    \\", "")
//					.trim();
            // ============================================================
            // ❌ REMOVED (OLD BUG)
            // mapper.readValue(aiJson, RecomendationRequest.class);
            //
            // This was causing:
            // Unrecognized field "analysis"
            // ============================================================
			//aiRecomendation = mapper.readValue(aiJson, RecomendationRequest.class);
			
            // ============================================================
            // Parse into AI DTO (correct structure)
            // ============================================================
			AiRecommendation ai =
			        mapper.readValue(aiJson, AiRecommendation.class);
			
            // ============================================================
            // Convert AI DTO → Request DTO using MapStruct
            // ============================================================
			aiRecomendation =
			        recomentationMapper.aiToRequest(
			                ai,
			                activity.activityId(),
			                activity.userId()
			        );		
			
			
			log.info(
				    "AI Recommendation processed | activityId={} | userId={} | recommendation={}",
				    aiRecomendation.getActivityId(),
				    aiRecomendation.getUserId(),
				    aiRecomendation.getAnalysis()
				);		
			
		}catch (Exception ex) {	
			// ============================================================
            // DO NOT THROW (prevents Kafka retry loop)
            // ============================================================
            log.error("AI parsing failed", ex);
			//throw new RuntimeException("AI parsing failed", ex);
            return null;
		}	
		
		return aiRecomendation;
	}


	private String createPromptForActivity(ActivityCreatedEventPayload activity) {
		log.info("ActivityAiService | createPromptForActivity.activityId: {} | activityType ", activity.activityId(), activity.activityType() );
	    return String.format("""
	        You are a professional fitness coach.

	        Analyze the following activity:

	        Activity Type: %s
	        Duration (minutes): %d
	        Calories Burned: %d
	        Additional Metrics: %s
	    		
	    		Provide detailed analysis focusing on performance, improvements, next workout suggestions 
	    		and safety guidelines.
	        Return the response strictly in this JSON format:

	        {
	          "analysis": {
	            "overall": "Overall analysis here",
	            "pace": "Pace analysis here",
	            "heartRate": "Heart rate analysis here",
	            "caloriesBurned": "Calories analysis here"
	          },
	          "improvements": [
	            {
	              "area": "Area name",
	              "recommendation": "Detailed recommendation"
	            }
	          ],
	          "suggestions": [
	            {
	              "workout": "Workout suggestion",
	              "description": "Detailed workout description"
	            }
	          ],
	          "safety": [	"Safety point 1",
	    						"Safety point 2"]
	        }
	        """,
	        activity.activityType(),
	        activity.duration(),
	        activity.caloriesBurned(),
	        activity.additionalMetrics()
	    );
	}		

}//Class ends
