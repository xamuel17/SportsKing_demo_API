package com.sport.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sport.payload.request.UpdateRequest;
import com.sport.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserService userservice;

	@ApiOperation(value = "User Profile", notes = "This fetches  logged in user details, Only account owners can access this resource")
	@GetMapping("/user/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> userProfile(@PathVariable("id") Long userId) {
		return userservice.fetchUserById(userId);
	}

	@ApiOperation(value = "Fetch All Users", notes = "View all registered users")
	@GetMapping("/users")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> allUsers() {
		return userservice.fetchAllUsers();
	}

	@ApiOperation(value = "Update user", notes = "This updates user's credentials: email, password or username")
	@PutMapping("/user")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateRequest UpdateRequest) {
		return userservice.updateUser(UpdateRequest);
	}

	@ApiOperation(value = "User Upload Profile Photo", notes = "This upload profile photo for user")
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
		return userservice.uploadPhoto(file);

	}

	@ApiOperation(value = "Activate Account", notes = "This activates your account")
	@GetMapping("/activate/{code}")
	public ResponseEntity<?> activateAccount(@PathVariable("code") String code) {
		return userservice.activate(code);
	}
	
	
	
	@ApiOperation(value = "View Profile Photo", notes = "view user profile pic")
	  @GetMapping("/uploads/{fileName:.+}")
	  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		  
		  return userservice.downloadFile(fileName, request);
	  }

}