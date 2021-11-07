package com.sport.service.implementation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.sport.payload.request.MailRequest;
import com.sport.service.EmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service(value = "emailService")
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private Configuration config;
	
	   private static final Logger LOGGER=LoggerFactory.getLogger(EmailServiceImpl.class);
	
	public Boolean sendTransactionEmail(MailRequest request, Map<String, Object> model) {
	
		boolean status = false;
		MimeMessage message = sender.createMimeMessage();
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// add attachment
			//helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

			Template t = config.getTemplate("registration-email-alert.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

			helper.setTo(request.getTo());
			helper.setText(html, true);
			helper.setSubject(request.getSubject());
			helper.setFrom(request.getFrom());
			sender.send(message);
			LOGGER.info("Activation email successfully sent to email:"+ request.getTo());
			status=true;

		} catch (MessagingException | IOException | TemplateException e) {
			LOGGER.error("Failure sending mail to "+request.getTo()+ "  Error:"+e.getMessage());
			status=false;
		}

		return status;
	}
	
	
	
	
	
	

}
