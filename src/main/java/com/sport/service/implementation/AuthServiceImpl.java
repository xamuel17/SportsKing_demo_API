package com.sport.service.implementation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sport.models.ERole;
import com.sport.models.Role;
import com.sport.models.User;
import com.sport.payload.request.LoginRequest;
import com.sport.payload.request.MailRequest;
import com.sport.payload.request.SignupRequest;
import com.sport.payload.response.JwtResponse;
import com.sport.payload.response.MessageResponse;
import com.sport.properties.EmailProperties;
import com.sport.repository.RoleRepository;
import com.sport.repository.UserRepository;
import com.sport.security.jwt.JwtUtils;
import com.sport.security.services.UserDetailsImpl;
import com.sport.service.AuthService;
import com.sport.service.EmailService;
import com.sport.util.StringUtil;

@Service(value = "authService")
public class AuthServiceImpl implements AuthService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	StringUtil stringUtil;

	@Autowired
	private EmailService emailService;

	@Autowired
	EmailProperties emailProps;

	@Autowired
	JwtUtils jwtUtils;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

	public ResponseEntity<?> createUser(SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new RuntimeException("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new RuntimeException("Error: Email is already in use!"));
		}

		// Create new user's account
		String interest = stringUtil.convertInterestToArray(signUpRequest.getInterest());
		String verificationCode = stringUtil.generateRandomString();
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPhone(),
				encoder.encode(signUpRequest.getPassword()), verificationCode, interest);

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		User userdata = userRepository.save(user);

		// Send email
		MailRequest request = new MailRequest();
		request.setFrom(emailProps.getAppEmail());
		request.setVerificationCode(verificationCode);
		request.setSubject("Welcome to SportKing");
		request.setTo(signUpRequest.getEmail());

		Map<String, Object> model = new HashMap<>();
		model.put("date", stringUtil.generateNowDate());
		model.put("verificationCode", verificationCode);

		try {
			emailService.sendTransactionEmail(request, model);
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}

		return ResponseEntity.ok(new MessageResponse<User>("User registered successfully!", 00, userdata));

	}

	public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
		Authentication authentication = null;

		if (loginRequest.getEmail() != null) {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		}

		if (loginRequest.getPhone() != null) {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getPhone(), loginRequest.getPassword()));
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Check if account is activated
		Boolean is_verified = userRepository.findWhereUserActive(loginRequest.getEmail());
		if (is_verified == true) {
			String jwt = jwtUtils.generateJwtToken(authentication);
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());
			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
					userDetails.getEmail(), roles));

		} else {

			return ResponseEntity.ok(new MessageResponse<Object>("Account not verified", 00, null));
		}

	}

//	public ResponseEntity<?> PasswordChange(String email){
//		
//	User user=userRepository.findByEmail(email);
//	
//		
//		return null;
//		
//		
//	}
}
