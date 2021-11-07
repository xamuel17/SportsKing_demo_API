package com.sport.service;

import java.util.Map;

import com.sport.payload.request.MailRequest;



public interface EmailService {
	Boolean sendTransactionEmail(MailRequest request, Map<String, Object> model);
}

