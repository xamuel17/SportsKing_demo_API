package com.sport.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sport.models.User;
import com.sport.payload.response.MessageResponse;
import com.sport.repository.UserRepository;
import com.sport.service.FilesStorageService;
import com.sport.service.UserService;
import com.sport.util.StringUtil;

import springfox.documentation.service.ResponseMessage;


@Service(value = "userService")
public class UserServiceImpl implements UserService{

	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StringUtil stringUtil;
	
	  @Autowired
	  FilesStorageService storageService;

	public ResponseEntity<?> fetchUserById(Long userId){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails currentPrincipalName = (UserDetails) authentication.getPrincipal();
		 System.out.println(currentPrincipalName.getUsername());
		 
		String value = currentPrincipalName.getUsername();
		 
		User userdata = userRepository.findByUserId(userId);
		
		if (stringUtil.isEmail(value)) {
			
			if (userdata.getEmail().equalsIgnoreCase(currentPrincipalName.getUsername()) ) {
				return ResponseEntity.ok(new MessageResponse<User>("success!",00,userdata));
				}else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
				}
		}else {
			
			if (userdata.getUsername().equalsIgnoreCase(currentPrincipalName.getUsername()) ) {
				return ResponseEntity.ok(new MessageResponse<User>("success!",00,userdata));
				}else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
				}
			
		}
		
	
			
	}
	
	
	

	public ResponseEntity<?> uploadPhoto(MultipartFile file , Long userId){
	    String message = "";
	    
	    try {
	      storageService.save(file);

	      message = "Uploaded the file successfully: " + file.getOriginalFilename();
	      return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<Object>( message, 00, null));
	    } catch (Exception e) {
	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse<Object>(message, -1001, null));
	}
	}
	
	
	
	public ResponseEntity<?> fetchAllUsers(){
		List <User> users = userRepository.findAll();
		if(users != null) {
			 return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<Object>( "success", 00, users));	
		}else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse<Object>("failed", -1001, null));	
		}
		
		
	}
	
	 
}
