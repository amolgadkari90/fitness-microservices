package com.fitness.userservice.service;


import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;

public interface UserService {
	
	UserResponse register(RegisterRequest registerRequest);

	UserResponse getUserById(Long id);

	Boolean existById(Long id);

	

}
