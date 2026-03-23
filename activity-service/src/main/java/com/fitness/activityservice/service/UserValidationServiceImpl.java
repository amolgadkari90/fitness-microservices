package com.fitness.activityservice.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fitness.activityservice.dto.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidationServiceImpl implements UserValidationService{
	
	private final WebClient userServiceWebClient;
	
	public Boolean validateUser(Long id){
		log.info("UserValidationService | validateUser: {}", id);
		
		try {
			
			ApiResponse<Boolean> response = userServiceWebClient.get()
			.uri("/api/v1/users/{id}/validate", id)
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<ApiResponse<Boolean>>() {})
			.block();
			
			log.info("isUserAvailable: {}", response.getData() );
			
			return response.getData();
			
		}catch(WebClientResponseException e) {
			log.error("isUserAvailable: false");
			e.printStackTrace(); //Exception just printed			
		}		
		return false;		
		
		
	}

}
