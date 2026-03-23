package com.fitness.activityservice.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fitness.activityservice.dto.ApiResponse;
import com.fitness.activityservice.enums.ResponseCode;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	ResponseEntity<ApiResponse<Object>> handleDbException(DataIntegrityViolationException ex){
		log.error("Database error", ex);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(ApiResponse.error(ResponseCode.USER_4001));
	}
	
	@ExceptionHandler(ActivityNotFoundException.class)
	ResponseEntity<ApiResponse<Object>> activityNotFoundException(ActivityNotFoundException ex){
		log.error("Database error", ex);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(ApiResponse.error(ResponseCode.USER_4001));
	}
	
	
	
}
