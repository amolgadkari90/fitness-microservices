package com.fitness.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.userservice.dto.ApiResponse;
import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.enums.ResponseCode;
import com.fitness.userservice.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	/*
	 * curl -X POST http://localhost:8081/api/v1/users/register -H "Content-Type: application/json" -d "{\"email\":\"user2@fitness.com\",\"password\":\"Password@123\",\"firstName\":\"Rohit\",\"lastName\":\"Verma\"}"
	 */

	@PostMapping("/register")
	ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {

		UserResponse registeredUser = userService.register(registerRequest);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(ResponseCode.USER_2001, registeredUser));
	}
	
	
	//curl -X GET http://localhost:8081/api/v1/users/1 -H "Content-Type: application/json" 
	@GetMapping ("/{id}")
	
	ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id){
		
		UserResponse user = userService.getUserById(id);
		
		return ResponseEntity.ok().body(ApiResponse.success(ResponseCode.USER_2000, user));
	}
	
	//curl -X GET http://localhost:8081/api/v1/users/1/validate -H "Content-Type: application/json" 
	@GetMapping ("/{id}/validate")
	
	ResponseEntity<ApiResponse<Boolean>> validateUser(@PathVariable Long id){
		
		Boolean userExist = userService.existById(id);
		
		return ResponseEntity.ok().body(ApiResponse.success(ResponseCode.USER_2000, userExist));
	}
	
	
	
	
	

}
/*
 * Curl commands Create -> curl -X POST
 * http://localhost:8080/api/v1/users/register -H
 * "Content-Type: application/json" -d
 * "{\"email\":\"user2@fitness.com\",\"password\":\"Password@123\",\"firstName\":\"Rohit\",\"lastName\":\"Verma\"}"
 * 
 * Read ->
 * 
 * Update ->
 * 
 * Delete ->
 * 
 */
