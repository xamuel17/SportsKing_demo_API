package com.sport.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport.models.ERole;
import com.sport.models.Role;
import com.sport.models.User;
import com.sport.payload.request.LoginRequest;
import com.sport.payload.request.SignupRequest;
import com.sport.payload.response.JwtResponse;
import com.sport.payload.response.MessageResponse;
import com.sport.repository.RoleRepository;
import com.sport.repository.UserRepository;
import com.sport.security.jwt.JwtUtils;
import com.sport.security.services.UserDetailsImpl;
import com.sport.service.AuthService;
import com.sport.util.StringUtil;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	
	@Autowired
	AuthService authService;
	
	
	 @ApiOperation( value= "User Signin Authentication", notes="This authenticates an existing user into the system using his registered username and password ")
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		 return authService.loginUser(loginRequest);
	}


	 @ApiOperation(value="User Signup Authentication", notes="This creates a new user using his login credentials")
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {	
		return authService.createUser(signUpRequest);
			}

}
