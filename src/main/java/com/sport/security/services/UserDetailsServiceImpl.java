package com.sport.security.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sport.models.User;
import com.sport.repository.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String value) throws UsernameNotFoundException {
		
		
	
		   String email = value;
	        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
	        Matcher mat = pattern.matcher(email);

	        if(mat.matches()){

	            System.out.println("Valid email address");
	    		User user = userRepository.findByEmail(email)
	    				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

	    		return UserDetailsImpl.build(user);
	        }else{
	        	String username = value;
	        	User user = userRepository.findByUsername(username)
    				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
	    		

	    		return UserDetailsImpl.build(user);
	        }
	    
	
		
	
	}
	
	
	

	@Transactional
	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
		
		
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

		return UserDetailsImpl.build(user);
	}

}
