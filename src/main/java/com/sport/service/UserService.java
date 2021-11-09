package com.sport.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.sport.payload.request.UpdateRequest;

public interface UserService {

	ResponseEntity<?> fetchUserById(Long userId);
	
	
	ResponseEntity<?> uploadPhoto(MultipartFile file);
	
	ResponseEntity<?> fetchAllUsers();
	
	ResponseEntity<?> activate(String code);


	ResponseEntity<?> updateUser( UpdateRequest updateRequest);



	ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request);
}
