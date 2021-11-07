package com.sport.payload.response;

public class MessageResponse <T> implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	
	private int status;
	
	private T data;
	
	public MessageResponse() {}
	
	
	
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}





	public MessageResponse(String message, int status, T data) {

		this.message = message;
		this.status = status;
		this.data = data;
	}
	
	

	
	

}
