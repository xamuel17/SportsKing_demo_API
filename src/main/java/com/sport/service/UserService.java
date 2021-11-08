package com.sport.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

	ResponseEntity<?> fetchUserById(Long userId);
	
	
	ResponseEntity<?> uploadPhoto(MultipartFile file , Long userId);
	
	ResponseEntity<?> fetchAllUsers();
}
