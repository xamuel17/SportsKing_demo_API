package com.sport.service.implementation;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.sport.models.User;
import com.sport.payload.request.UpdateRequest;
import com.sport.payload.response.MessageResponse;
import com.sport.repository.UserRepository;
import com.sport.service.FilesStorageService;
import com.sport.service.UserService;
import com.sport.util.StringUtil;

import springfox.documentation.service.ResponseMessage;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
	
	

	

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	StringUtil stringUtil;

	@Autowired
	FilesStorageService storageService;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);  

	public ResponseEntity<?> fetchUserById(Long userId) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails currentPrincipalName = (UserDetails) authentication.getPrincipal();
		System.out.println(currentPrincipalName.getUsername());

		String value = currentPrincipalName.getUsername();
		
		User userdata = userRepository.findByUserId(userId);

		if (stringUtil.isEmail(value)) {

			if (userdata.getEmail().equalsIgnoreCase(value)) {
				return ResponseEntity.ok(new MessageResponse<User>("success!", 00, userdata));
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			}
		} else {

			if (userdata.getUsername().equalsIgnoreCase(value)) {
				return ResponseEntity.ok(new MessageResponse<User>("success!", 00, userdata));
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			}

		}

	}

	public ResponseEntity<?> uploadPhoto(MultipartFile file) {
		String message = "";

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails currentPrincipalName = (UserDetails) authentication.getPrincipal();
		String uname = currentPrincipalName.getUsername();
		

		User user = userRepository.findByUname(uname);
		
		try {
			User userdata = storageService.save(file,user);
			userRepository.save(userdata);
			message = "Uploaded the file successfully: " + userdata.getPhoto();
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<Object>(message, 00, null));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new MessageResponse<Object>(message, -1001, null));
		}
	}

	public ResponseEntity<?> fetchAllUsers() {
		List<User> users = userRepository.findAll();
		if (users != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<Object>("success", 00, users));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new MessageResponse<Object>("failed", -1001, null));
		}

	}

	public ResponseEntity<?> activate(String code) {

		User user = userRepository.findByVerificationCode(code);
		// activate account
		user.setVerified(true);
		User isVerfied = userRepository.save(user);
		if (isVerfied != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<Object>("success", 00, isVerfied));

		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new MessageResponse<Object>("failed", -1001, null));

		}
	}

	public ResponseEntity<?> updateUser(UpdateRequest updateRequest) {

		List<String> data = updateRequest.getUpdateRequest();

		System.out.println(data.toString());
		String email = data.get(0);
		String username = data.get(1);
		String password = encoder.encode(data.get(2));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails currentPrincipalName = (UserDetails) authentication.getPrincipal();
		String uname = currentPrincipalName.getUsername();

		User user = userRepository.findByUname(uname);
		if (email != null) {
			user.setEmail(email);
		}
		if (username != null) {
			user.setUsername(username);
		}
		if (password != null) {
			user.setPassword(password);
		}

		User userdata = userRepository.save(user);
		if (userdata != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<Object>("success", 00, userdata));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new MessageResponse<Object>("failed", -1001, null));

		}

	}
	
	
	
	
	
	
	
	
	
	
	

	  public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request) {
	      // Load file as Resource
	      Resource resource = storageService.loadFileAsResource(fileName);

	      // Try to determine file's content type
	      String contentType = null;
	      try {
	          contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	      } catch (IOException ex) {
	    	  LOGGER.info("Could not determine file type.");
	      }

	      // Fallback to the default content type if type could not be determined
	      if(contentType == null) {
	          contentType = "application/octet-stream";
	      }

	      return ResponseEntity.ok()
	              .contentType(MediaType.parseMediaType(contentType))
	              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	              .body(resource);
	  }
	  
	  



}
