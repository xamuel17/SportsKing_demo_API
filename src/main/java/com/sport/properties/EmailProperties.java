package com.sport.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration("emailConfigProperties")
public class EmailProperties {

	   @Value("${spring.mail.username}")
	    private String appEmail;
	   
	   
	   
	   
	   public  EmailProperties() {}

	public String getAppEmail() {
		return appEmail;
	}

	public void setAppEmail(String appEmail) {
		this.appEmail = appEmail;
	}

	
}
