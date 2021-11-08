package com.sport.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.sport.service.UserService;
import io.swagger.annotations.ApiOperation;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {
	
	
	
	@Autowired
	UserService userservice;
	
	
	

	 @ApiOperation(value="User Profile", notes="This fetches  logged in user details, Only account owners can access this resource")
	@GetMapping("/user/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> userProfile(@PathVariable("id") Long userId) {
		return userservice.fetchUserById(userId);	
	}

	 
	 
	 @ApiOperation(value="User Upload Profile Photo", notes="This upload profile photo for user")
	@PreAuthorize("hasRole('USER')")
	  @PostMapping("/upload/{id}")
	  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,@PathVariable("id") Long userId ) {
		return userservice.uploadPhoto(file, userId);
	    
	  }
	  
	  
	 @ApiOperation(value="Fetch All Users", notes="View all registered users")
	@GetMapping("/users")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> allUsers() {
		return userservice.fetchAllUsers();
	}

	 
	 

		
		@GetMapping("/all")
		public String allAccess() {
			return "Public Content.";
		}

}