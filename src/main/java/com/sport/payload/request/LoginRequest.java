package com.sport.payload.request;

public class LoginRequest {
	
	
	private String password;
	private String email;
	private String phone;
	
	
	
	public LoginRequest() {}
	
	
	

	public LoginRequest(String password, String email, String phone) {
		super();
		this.password = password;
		this.email = email;
		this.phone = phone;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}




	public String getPhone() {
		return phone;
	}




	public void setPhone(String phone) {
		this.phone = phone;
	}



	
	

}
