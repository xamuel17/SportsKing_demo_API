package com.sport.payload.request;

public class MailRequest {
	
	private String to;
	private String subject;
	private String from;
	private String verificationCode;
	
	
	
	
	
	
	public MailRequest() {

	}
	public MailRequest(String to, String subject, String from, String verificationCode) {
		super();
		this.to = to;
		this.subject = subject;
		this.from = from;
		this.verificationCode = verificationCode;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	
	
	

}
