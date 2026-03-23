package com.fitness.userservice.service;



import org.springframework.stereotype.Service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.enums.ResponseCode;
import com.fitness.userservice.exception.UserAlreadyExistsException;
import com.fitness.userservice.exception.UserNotFoundException;
import com.fitness.userservice.models.User;
import com.fitness.userservice.models.UserRole;
import com.fitness.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
	
	 private final UserRepository userRepository;

	    @Override
	    public UserResponse register(RegisterRequest registerRequest) {

	        if (registerRequest == null) {
	        		
	        		
	            throw new IllegalArgumentException("Register request cannot be null");
	        }

	        if (userRepository.existsByEmail(registerRequest.getEmail())) {
	        	log.info(ResponseCode.USER_4090.code() + ": "+registerRequest.getEmail()+" " + ResponseCode.USER_4090.message());
	            throw new UserAlreadyExistsException(
	                ResponseCode.USER_4090.message()
	            );
	        }

	        User user = User.builder()
	                .email(registerRequest.getEmail())
	                .firstName(registerRequest.getFirstName())
	                .lastName(registerRequest.getLastName())
	                .password(registerRequest.getPassword())
	                .role(UserRole.USER)
	                .build();

	        User savedUser = userRepository.save(user);

	        return UserResponse.builder()
	                .id(savedUser.getId())
	                .email(savedUser.getEmail())
	                .firstName(savedUser.getFirstName())
	                .lastName(savedUser.getLastName())
	                .updatedAt(savedUser.getUpdatedAt())
	                .build();
	    }

		@Override
		public UserResponse getUserById(Long id) {
			// TODO Auto-generated method stub
			
			 if (id == null) {
	        			        		
		            throw new IllegalArgumentException("Register request cannot be null");
		        }
			 
			 
			 User user = userRepository.findById(id).get();
		        
		        
		        if (user == null) {
		        	log.info(ResponseCode.USER_4004.code() + ": "+id+" " + ResponseCode.USER_4004.message());
		            throw new UserNotFoundException(
		                ResponseCode.USER_4004.message()	
		            );
		        }
		        
		        


		        return UserResponse.builder()
		                .id(user.getId())
		                .email(user.getEmail())
		                .firstName(user.getFirstName())
		                .lastName(user.getLastName())
		                .updatedAt(user.getUpdatedAt())
		                .build();			
			
		}

		@Override
		public Boolean existById(Long id) {
			
			boolean existsById = userRepository.existsById(id);
			
			return existsById;
		}


	
	
	
	

}
