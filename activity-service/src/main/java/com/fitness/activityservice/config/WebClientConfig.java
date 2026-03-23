package com.fitness.activityservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	@Bean
	@LoadBalanced
	WebClient.Builder webClientBuider(){	
		/*
		 * WebClient is an interface 
		 * Builder is nested interface inside the WebClient
		 * WebClient has a method called Builder which returns new DefaultWebClientBuilder() ;
		 * DefaultWebClientBuilder is the class  which implements WebClient.Builder interface 
		 * that is it implements Builder interface and that builder Interface is nested within 
		 * WebClient interface 
		 * With this we are getting DefaultWebClientBuilder class object
		 * */
		return WebClient.builder();		
	}
	
	@Bean
	WebClient userServiceWebClient(WebClient.Builder webClientBuider) {
		return webClientBuider.baseUrl("http://USER-SERVICE").build();
	}
	
	
	

}
