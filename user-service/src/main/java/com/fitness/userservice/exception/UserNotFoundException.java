package com.fitness.userservice.exception;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5487313687667247618L;

	
	public UserNotFoundException(String message){
		super(message);
	}
	

}
