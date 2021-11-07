package com.sport.service;

import org.springframework.http.ResponseEntity;

import com.sport.payload.request.LoginRequest;
import com.sport.payload.request.SignupRequest;

public interface AuthService {

	
	ResponseEntity<?> createUser(SignupRequest signUpRequest);
	public ResponseEntity<?> loginUser(LoginRequest loginRequest);
}
