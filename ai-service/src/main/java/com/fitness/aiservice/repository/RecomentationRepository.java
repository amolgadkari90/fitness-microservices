package com.fitness.aiservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitness.aiservice.model.Recomendation;

@Repository
public interface RecomentationRepository extends MongoRepository<Recomendation, String> {

	List<Recomendation> getRecomentationsByUserId(Long userId);
	List<Recomendation> getRecomendationByactivityId(String activityId);

}
