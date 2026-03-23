package com.fitness.userservice.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	@Column(name = "user_email", nullable = false, unique = true, length = 100)
	private String email;

	@NotBlank(message = "Password is required")
	@Column(name = "user_password", nullable = false, length = 60)
	@Size(min = 6, message = "Password must be 6 char or more")
	private String password;

	@NotBlank(message = "First name is required")
	@Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
	@Column(name = "user_first_name", nullable = false, length = 50)
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
	@Column(name = "user_last_name", nullable = false, length = 50)
	private String lastName;

}
