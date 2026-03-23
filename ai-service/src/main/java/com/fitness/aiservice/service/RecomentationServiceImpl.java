package com.fitness.aiservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.aiservice.dto.RecomendationRequest;
import com.fitness.aiservice.dto.RecomendationResponse;
import com.fitness.aiservice.mapper.RecomentationMapper;
import com.fitness.aiservice.model.Recomendation;
import com.fitness.aiservice.repository.RecomentationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecomentationServiceImpl implements RecomentationService{
	
	private final  RecomentationRepository  recomentationRepository;
	
	private final RecomentationMapper mapper;

	@Override
	public List<RecomendationResponse> getRecomentationsByUserId(Long userId) {
		log.info("RecomentationServiceImpl | getRecomentationsByUserId: ", userId);
		List<Recomendation> listOfUserRecomedations= recomentationRepository.getRecomentationsByUserId(userId);
		List<RecomendationResponse> listOfUserRecomendationResponses = mapper.entityToResponse(listOfUserRecomedations);
		return listOfUserRecomendationResponses;
	}


	@Override
	public List<RecomendationResponse> getRecomendationByactivityId(String activityId) {
		log.info("RecomentationServiceImpl | getRecomendationByactivityID : ", activityId);
		List<Recomendation> listOfActivityRecomendations = recomentationRepository.getRecomendationByactivityId(activityId);
		List<RecomendationResponse> listOfActivityRecomendationResponses = mapper.entityToResponse(listOfActivityRecomendations);
		return listOfActivityRecomendationResponses;
	}


	@Override
	public Recomendation saveRecomendation(RecomendationRequest recomendationRequest) {
		
		//Check null
		if(recomendationRequest == null) {
			throw new RuntimeException("Recommendation can't be saved recomendationRequest object is empty");
		}
		
		//Map to recommendation object 
		Recomendation requestToEntity = mapper.requestToEntity(recomendationRequest);
		
		
		//if save
		Recomendation savedRecommendation = recomentationRepository.save(requestToEntity);
		
		
		//return the saved object 
		
		return savedRecommendation;
	}
	
	
	
	
	

}
