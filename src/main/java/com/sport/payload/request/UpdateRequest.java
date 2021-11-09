package com.sport.payload.request;

import java.util.ArrayList;
import java.util.List;


public class UpdateRequest {


	private String email;
	
	
	private String username;
	
	

	private String password;
	
	List<String> arr;
	
	public UpdateRequest() {
		
	}
	
	

	
	
	public List<String> getUpdateRequest() {
//		super();
//		this.email = email;
//		this.username = username;
//		this.password = password;
	 arr =new ArrayList<String>();
		arr.add(this.email);
		arr.add(this.username);
		arr.add(this.password);
		return arr;
	}





	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
