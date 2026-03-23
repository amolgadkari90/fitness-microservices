package com.fitness.activityservice.exceptions;

import org.springframework.lang.Nullable;


public class ActivityNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ActivityNotFoundException(@Nullable String msg) {
		super(msg);
	}	
	
	public ActivityNotFoundException(@Nullable String msg, @Nullable Throwable cause) {
		super(msg, cause);
	}

}
